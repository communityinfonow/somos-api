package info.cinow.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.Location;
import info.cinow.model.Photo;
import info.cinow.repository.CensusTractDao;
import info.cinow.repository.PhotoDao;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoServiceImpl
 */
@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private CensusTractDao censusTractDao;

    @Autowired
    private AmazonS3Client amazonS3Client;

    /**
     * Name of S3 bucket to which photos are saved.
     */
    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public Location getGpsCoordinates(Long id) {
        Photo photo = this.photoDao.findById(id).orElse(new Photo());
        return new Location(photo.getLatitude(), photo.getLongitude()); // TODO test nulls?
    }

    @Override
    public List<PhotoDto> uploadPhotos(MultipartFile[] photos) throws IOException {
        List<PhotoDto> photoEntities = new ArrayList<PhotoDto>();
        for (MultipartFile photo : photos) {
            File convertedFile = convertMultipartFileToFile(photo);
            Photo savedPhotoInfo = savePhotoInformationToDatabase(convertedFile); // save photo info to db and return
                                                                                  // entity
            photoEntities.add(PhotoMapper.toPhotoDto(savedPhotoInfo));
            uploadPhotoToS3Bucket(savedPhotoInfo.getFileName(), convertedFile); // use name from database to save file
                                                                                // to S3 Bucket
            FileUtils.deleteQuietly(convertedFile.getParentFile()); // clean up temp file TODO: unit test
        }
        return photoEntities;
    }

    public PhotoDto updatePhoto(Photo photo) {
        return PhotoMapper.toPhotoDto(photoDao.save(photo));
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
        List<GeoLocation> locations = extractGPSMetadata(file);
        Photo photo = new Photo();
        // TODO: figure out why there are more than one possibly being created in the
        // first place
        if (locations.size() == 1) {
            photo.setLongitude(locations.get(0).getLongitude());
            photo.setLatitude(locations.get(0).getLatitude());
        }
        // photo.setTractId(censusTractDao.getContainingTract(photo.getLongitude(),
        // photo.getLatitude()));
        photo.setFileName(file.getName());
        return this.photoDao.save(photo);
    }

    private void uploadPhotoToS3Bucket(String photoName, File photo) throws IOException {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, photoName, photo));
    }

    // TODO: Unit tests such as same file name
    private List<GeoLocation> extractGPSMetadata(File photo) {
        List<GeoLocation> geoLocations = new ArrayList<GeoLocation>();
        Metadata metadata;
        Collection<GpsDirectory> gpsDirectories = new ArrayList<GpsDirectory>();
        try {
            metadata = ImageMetadataReader.readMetadata(photo);
            gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
        } catch (ImageProcessingException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO figure out why there's more than one possible here
        for (GpsDirectory gpsDirectory : gpsDirectories) {
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            if (geoLocation != null && !geoLocation.isZero()) {
                geoLocations.add(geoLocation);
            }
        }
        return geoLocations;
    }

}