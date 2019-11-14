package info.cinow.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.model.Location;
import info.cinow.model.Photo;
import info.cinow.repository.PhotoDao;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoServiceImpl
 */
@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private PhotoDao photoDao;

    /**
     * Name of S3 bucket to which photos are saved.
     */
    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public Optional<Location> getGpsCoordinates(Long id) {
        Photo photo = this.photoDao.findById(id).orElse(null);
        Optional<Location> location = Optional.empty();
        if (photo != null && photo.getLatitude() != null && photo.getLongitude() != null) {
            location = Optional.of(new Location(photo.getLatitude(), photo.getLongitude()));
        }
        return location;
    }

    @Override
    public List<Photo> uploadPhotos(MultipartFile[] photos) throws IOException {
        List<Photo> photoEntities = new ArrayList<Photo>();
        for (MultipartFile photo : photos) {
            File convertedFile = convertMultipartFileToFile(photo);
            Photo savedPhotoInfo = savePhotoInformationToDatabase(convertedFile);
            Photo savedPhoto = this.savePhoto(photo, savedPhotoInfo, convertedFile);
            photoEntities.add(savedPhoto);
        }
        return photoEntities;
    }

    private Photo savePhoto(MultipartFile photo, Photo savedPhotoInfo, File photoFile) {

        try {

            uploadPhotoToS3Bucket(savedPhotoInfo.getFilePathName(), photoFile);
        } catch (Exception e) {
            log.error("An error occured saving photo", e);
            if (!(e instanceof DataAccessException)) {
                photoDao.delete(savedPhotoInfo); // if error isn't from JPA, delete photo in database
            }
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Photo could not be saved");
        } finally {
            FileUtils.deleteQuietly(photoFile); // clean up temp file
        }
        return savedPhotoInfo;
    }

    @Override
    public Photo replacePhoto(Long id, MultipartFile photo) {
        File convertedFile = convertMultipartFileToFile(photo);
        Photo savedPhotoInfo = photoDao.findById(id).orElse(null); // TODO: actually handle this
        return this.savePhoto(photo, savedPhotoInfo, convertedFile);
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        return photoDao.save(photo);
        // TODO: update name of file on S3
    }

    @Override
    public byte[] getPhoto(String fileName) throws IOException {
        return loadPhotoFromS3Bucket(fileName);
    }

    @Override
    public Optional<Photo> getPhoto(Long id) {
        return this.photoDao.findById(id);
    }

    @Override
    public void deletePhoto(Long id) throws IOException { // TODO: handle this
        Photo photo = this.photoDao.findById(id).orElseThrow(EntityNotFoundException::new); // TODO: handle this
                                                                                            // somewhere
        this.photoDao.deleteById(id);
        this.deletePhotoFromS3Bucket(photo.getFilePathName());

    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private Photo savePhotoInformationToDatabase(File file) {
        GeoLocation location = extractGPSMetadata(file).orElse(new GeoLocation(0, 0));
        Photo photo = new Photo();
        if (!location.isZero()) {
            photo.setLongitude(location.getLongitude());
            photo.setLatitude(location.getLatitude());
        }

        photo.setFileName(file.getName());
        return this.photoDao.save(photo);
    }

    private void uploadPhotoToS3Bucket(String photoName, File photo) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, photoName, photo));
    }

    private byte[] loadPhotoFromS3Bucket(String photoName) throws IOException {
        S3ObjectInputStream stream = amazonS3Client.getObject(bucketName, photoName).getObjectContent();
        return IOUtils.toByteArray(stream);
    }

    private void deletePhotoFromS3Bucket(String photoName) throws IOException {
        amazonS3Client.deleteObject(bucketName, photoName);
    }

    private Optional<GeoLocation> extractGPSMetadata(File photo) {
        GeoLocation geoLocation = null;
        Metadata metadata;
        Collection<GpsDirectory> gpsDirectories = new ArrayList<GpsDirectory>();
        try {
            metadata = ImageMetadataReader.readMetadata(photo);
            gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
        } catch (ImageProcessingException | IOException e) {
            log.error("An error occurred extracting GPS metadata.", e);
        }
        if (gpsDirectories.size() == 1) {
            geoLocation = gpsDirectories.stream().findFirst().orElse(new GpsDirectory()).getGeoLocation();
        }
        return geoLocation == null ? Optional.empty() : Optional.of(geoLocation);
    }

    @Override
    public List<Photo> getPhotos() {
        List<Photo> photos = new ArrayList<Photo>();
        photoDao.findAll().forEach(photo -> {
            photos.add(photo);
        });
        return photos;
    }

    @Override
    public Photo replacePhoto(MultipartFile photo, Long photoId) {
        // TODO Auto-generated method stub
        return null;
    }

}