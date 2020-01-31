package info.cinow.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
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

import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.Location;
import info.cinow.model.Photo;
import info.cinow.repository.PhotoDao;
import info.cinow.utility.FileSaveErrorHandling;
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

    @Autowired
    private FileSaveErrorHandling fileErrorHandling;

    /**
     * Name of S3 bucket to which photos are saved.
     */
    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public List<Photo> getAllPhotos() {
        List<Photo> photos = new ArrayList<Photo>();
        photoDao.findAll().forEach(photo -> {
            photos.add(photo);
        });
        return photos;
    }

    @Override
    public Optional<Location> getGpsCoordinates(Long id) {
        Photo photo = this.photoDao.findById(id).orElse(null);
        Optional<Location> location = Optional.empty();
        if (photo != null && photo.getLatitude() != null && photo.getLongitude() != null) {
            location = Optional.of(new Location(photo.getLatitude().orElse(null), photo.getLongitude().orElse(null)));
        }
        return location;
    }

    @Override
    public Photo uploadPhoto(MultipartFile photo)
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException {
        this.fileErrorHandling.checkContentType(photo);
        this.fileErrorHandling.checkFileSize(photo);
        this.fileErrorHandling.checkFileNameSize(photo.getOriginalFilename());
        File convertedFile = this.convertMultipartFileToFile(photo);
        Photo savedPhotoInfo = this.savePhotoInformationToDatabase(convertedFile);
        this.saveImageFile(photo, savedPhotoInfo, convertedFile, false);
        return savedPhotoInfo;
    }

    @Override
    public Photo cropPhoto(MultipartFile photo, Long id)
            throws ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException, IOException {
        this.fileErrorHandling.checkContentType(photo);
        this.fileErrorHandling.checkFileSize(photo);
        this.fileErrorHandling.checkFileNameSize(photo.getOriginalFilename());
        File convertedFile;

        convertedFile = convertMultipartFileToFile(photo);

        Photo originalPhotoInfo = photoDao.findById(id).get();
        this.saveImageFile(photo, originalPhotoInfo, convertedFile, true);

        return originalPhotoInfo;
    }

    @Override
    public Photo updatePhoto(Photo photo) throws NoDescriptionException, CensusTractDoesNotExistException {

        // photo can't be approved until description is complete
        photo = this.convertToEntity(photo);
        if (photo.getApproved().orElse(false) && photo.getDescription().orElse("").isEmpty()) {
            throw new NoDescriptionException(photo.getFilePathName());
        }
        if (!photo.getCensusTract().isPresent()) {
            throw new CensusTractDoesNotExistException(photo.getFilePathName());
        }
        this.updateS3FileName(photo.getId(), photo.getFileName().get(), photo.getFilePathName());
        return photoDao.save(photo);

        // TODO test s3 name update

    }

    /**
     * Updates the file path/name in S3 if the name has been updated
     * 
     * @param photo
     */
    private void updateS3FileName(Long photoId, String photoName, String photoPath) {
        // TODO test
        Photo oldPhoto = this.photoDao.findById(photoId).get();
        if (!oldPhoto.getFileName().get().equals(photoName)) {
            amazonS3Client.copyObject(bucketName, oldPhoto.getFilePathName(), bucketName, photoPath);
            amazonS3Client.deleteObject(bucketName, oldPhoto.getFilePathName());
        }
    }

    @Override
    public byte[] getPublicPhotoByFileName(String fileName) throws IOException {
        return loadPhotoFromS3Bucket(fileName);
    }

    @Override
    public Optional<Photo> getPhotoById(Long id) {
        return this.photoDao.findById(id);
    }

    @Override
    public void deletePhoto(Long id) throws IOException {
        Photo photo = this.photoDao.findById(id).get();
        this.photoDao.deleteById(id);
        this.deletePhotoFromS3Bucket(photo.getFilePathName());
    }

    /**
     * Saves the image file.
     * 
     * @param photo
     * @param savedPhotoInfo
     * @param photoFile
     * @param isCropped
     * @return the saved photo
     */
    private void saveImageFile(MultipartFile photo, Photo savedPhotoInfo, File photoFile, boolean isCropped) {

        String filePath = this.determineS3PhotoFilePath(savedPhotoInfo, isCropped);
        try {
            uploadPhotoToS3Bucket(filePath, photoFile);
        } catch (Exception e) {
            log.error("An error occured saving photo:" + savedPhotoInfo.toString(), e);
            if (!(e instanceof DataAccessException)) {
                photoDao.delete(savedPhotoInfo); // if error isn't from JPA, delete photo in database
            }
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Photo could not be saved"); // TODO move this
                                                                                                       // throw to the
                                                                                                       // controller
        } finally {
            FileUtils.deleteQuietly(photoFile); // clean up temp file
        }

    }

    /**
     * Determines the file path in S3 of a photo based on whether it is cropped or
     * not.
     * 
     * @param photo
     * @param isPhotoCropped
     * @return the file path
     */
    private String determineS3PhotoFilePath(Photo photo, boolean isPhotoCropped) {
        return isPhotoCropped ? photo.getCroppedFilePathName() : photo.getFilePathName();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private Photo savePhotoInformationToDatabase(File file) {
        GeoLocation location = extractGPSMetadata(file).orElse(new GeoLocation(0, 0));
        Photo photo = new Photo();
        if (!location.isZero()) {
            photo.setLongitude(location.getLongitude());
            photo.setLatitude(location.getLatitude());
        }

        if (!photo.getApproved().isPresent()) {
            photo.setApproved(false);
        }

        photo.setFileName(this.replaceFileNameWhitespace(file.getName()));
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

    private String replaceFileNameWhitespace(String fileName) {
        return fileName.replaceAll("\\s", "_");
    }

    /**
     * Extracts GPS metadata from photo
     * 
     * @param photo
     * @return the GPS coordinates
     */
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

    /**
     * Converts the photo into an entity that already exists by first querying for
     * the entity then modifying it.
     * 
     * @param photo the updated photo
     * @return the altered entity
     */
    private Photo convertToEntity(Photo photo) {
        if (photo.getId() != null) {
            try {
                Photo oldPhoto = this.getPhotoById(photo.getId()).get();
                photo.setCensusTract(oldPhoto.getCensusTract().orElse(photo.getCensusTract().orElse(null)));
                photo.setOwnerEmail(oldPhoto.getOwnerEmail().orElse(photo.getOwnerEmail().orElse(null)));
                photo.setOwnerFirstName(oldPhoto.getOwnerFirstName().orElse(photo.getOwnerFirstName().orElse(null)));
                photo.setOwnerLastName(oldPhoto.getOwnerLastName().orElse(photo.getOwnerLastName().orElse(null)));
                photo.setLatitude(oldPhoto.getLatitude().orElse(photo.getLatitude().orElse(null)));
                photo.setLongitude(oldPhoto.getLongitude().orElse(photo.getLongitude().orElse(null)));
                if (StringUtils.isNullOrEmpty(photo.getFileName().orElse(null))) {
                    photo.setFileName(oldPhoto.getFileName().orElse("placeholder_name"));
                }

            } catch (NoSuchElementException e) {
                log.info("No photo exists for id: " + photo.getId(), e);
            }
        }
        return photo;
    }

}