package info.cinow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line
import org.springframework.web.server.ResponseStatusException;

import info.cinow.audit.Audit;
// import info.cinow.authentication.User;
import info.cinow.dto.PhotoDto;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.repository.PhotoDao;

/**
 * GeocodeDaoTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoServiceImplTest {

    @MockBean
    private PhotoDao photoDao;

    @Autowired
    private PhotoService service;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    private MockMultipartFile mockFile;

    private MockMultipartFile mockFileLongName;

    private MockMultipartFile mockFileLargeSize;

    private MockMultipartFile mockFileWrongType;

    private Photo returnPhoto;

    @Before
    public void setup() throws Exception {

        PhotoDto dto = new PhotoDto();
        dto.setId(1L);
        dto.setFileName("photo");
        returnPhoto = new Photo();
        returnPhoto.setFileName("photo");
        returnPhoto.setId(1L);
        returnPhoto.setApproved(true);
        returnPhoto.setDescription("description");
        returnPhoto.setCensusTract(new CensusTract());
        // User user = new User();
        // user.setFirstName("First");
        // user.setLastName("last");
        Audit audit = new Audit();
        audit.setLastModified(LocalDateTime.now());
        // audit.setLastModifiedBy(user);
        returnPhoto.setAudit(audit);
        mockFile = new MockMultipartFile("fileThatDoesNotExists.jpeg", "fileThatDoesNotExists.jpeg", "image/jpeg",
                new FileInputStream(
                        new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));
        ;

        mockFileLongName = new MockMultipartFile("photo", StringUtils.repeat("j", 1010) + ".jpeg", "image/jpeg",
                new FileInputStream(
                        new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));

        mockFileWrongType = new MockMultipartFile("photo", "name.jpeg", "text/plain", new FileInputStream(
                new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));

        File bigFile = new File("bigFile");

        RandomAccessFile raf = new RandomAccessFile(bigFile, "rw");
        raf.setLength(400000000);
        raf.close();
        mockFileLargeSize = new MockMultipartFile("photo", "name.jpeg", "image/jpeg", new FileInputStream(bigFile));

        Mockito.when(photoDao.save(any(Photo.class))).thenReturn(returnPhoto);

    }

    @Test
    public void photoUpdates() throws Exception {
        Photo response = this.service.updatePhoto(returnPhoto);
        assertEquals(returnPhoto, response);
    }

    @Test
    public void photoHasEditedInfo() {
        Mockito.when(photoDao.findById(returnPhoto.getId())).thenReturn(Optional.of(returnPhoto));
        Photo photo = this.service.getPhotoById(1L).orElse(null);
        assertNotNull(photo);
        assertNotNull(photo.getAudit());
        assertNotNull(photo.getAudit().getLastModified());
        // assertNotNull(photo.getAudit().getLastModifiedBy());
        // assertEquals(photo.getAudit().getLastModifiedBy().getFirstName(), "First");

    }

    @Test(expected = NoDescriptionException.class)
    public void approveWithoutDescription_ThrowsErrors()
            throws NoDescriptionException, CensusTractDoesNotExistException {
        Photo withoutDescr = this.returnPhoto;
        withoutDescr.setDescription(null);
        this.service.updatePhoto(withoutDescr);
    }

    @Test(expected = CensusTractDoesNotExistException.class)
    public void updateWithoutTract_ThrowsErrors() throws NoDescriptionException, CensusTractDoesNotExistException {
        Photo withoutTract = this.returnPhoto;
        withoutTract.setCensusTract(null);
        this.service.updatePhoto(withoutTract);
    }

    @Test(expected = ImageNameTooLongException.class)
    public void uploadPhotoWithLongName_ExpectError()
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException {
        Mockito.when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        this.service.uploadPhoto(mockFileLongName);
    }

    @Test(expected = WrongFileTypeException.class)
    public void uploadPhotoWithWrongContentType_ExpectError()
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException {
        Mockito.when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        this.service.uploadPhoto(mockFileWrongType);
    }

    @Test(expected = ImageTooLargeException.class)
    public void uploadPhotoWithLargeSize_ExpectError()
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException {
        Mockito.when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        this.service.uploadPhoto(mockFileLargeSize);
    }

    @Test
    public void photoDeletedFromLocalServer() throws Exception {
        Mockito.when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        MockMultipartFile file = mockFile;
        service.uploadPhoto(file);
        File savedFile = new File(file.getOriginalFilename());
        assertFalse(savedFile.exists());
    }

    @Test
    public void savesCroppedPhotoToS3()
            throws ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException, IOException {
        Mockito.when(photoDao.findById(returnPhoto.getId())).thenReturn(Optional.of(returnPhoto));
        service.cropPhoto(mockFile, 1L);
        assertTrue(amazonS3Client.doesObjectExist(bucketName, returnPhoto.getCroppedFilePathName()));
        amazonS3Client.deleteObject(bucketName, returnPhoto.getCroppedFilePathName());
    }

    @Test
    public void uploadPhotoS3()
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException {
        Mockito.when(photoDao.findById(returnPhoto.getId())).thenReturn(Optional.of(returnPhoto));
        service.uploadPhoto(mockFile);
        assertTrue(amazonS3Client.doesObjectExist(bucketName, returnPhoto.getFilePathName()));
        amazonS3Client.deleteObject(bucketName, returnPhoto.getFilePathName());
    }

    @Test
    public void updatePhotoName_UpdatesInBothPlaces() {
        // TODO: test for updating file name and updates in s3 and database
    }

    @Test
    public void savedImageStripsMetadata() {
        // TODO: test for stripped metadata
    }

    @Test
    public void getImageByFileName() {
        // TODO: test getting image by file name
    }

}