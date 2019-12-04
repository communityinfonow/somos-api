package info.cinow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3Client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line

import info.cinow.audit.Audit;
import info.cinow.authentication.User;
import info.cinow.dto.PhotoDto;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.NoDescriptionException;
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

    private MockMultipartFile[] files;

    private MockMultipartFile mockFile;

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
        User user = new User();
        user.setFirstName("First");
        user.setLastName("last");
        Audit audit = new Audit();
        audit.setLastModified(LocalDateTime.now());
        audit.setLastModifiedBy(user);
        returnPhoto.setAudit(audit);
        mockFile = new MockMultipartFile("fileThatDoesNotExists.jpeg", "fileThatDoesNotExists.jpeg", "image/jpeg",
                new FileInputStream(
                        new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));
        ;

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
        Photo photo = this.service.getPhoto(1L).orElse(null);
        assertNotNull(photo);
        assertNotNull(photo.getAudit());
        assertNotNull(photo.getAudit().getLastModified());
        assertNotNull(photo.getAudit().getLastModifiedBy());
        assertEquals(photo.getAudit().getLastModifiedBy().getFirstName(), "First");

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

    @Test
    public void photoDeletedFromLocalServer() throws Exception {

        MockMultipartFile[] files = new MockMultipartFile[1];
        files[0] = mockFile;
        service.uploadPhotos(files);
        File savedFile = new File(files[0].getOriginalFilename());
        assertFalse(savedFile.exists());
    }

    @Test
    public void savesCroppedPhotoToS3() {
        Mockito.when(photoDao.findById(returnPhoto.getId())).thenReturn(Optional.of(returnPhoto));
        service.cropPhoto(mockFile, 1L);
        assertTrue(amazonS3Client.doesObjectExist(bucketName, returnPhoto.getCroppedFilePathName()));
        amazonS3Client.deleteObject(bucketName, returnPhoto.getCroppedFilePathName());
    }

    // TODO: test for stripped metadata

    // TODO: what if there isn't a file name?

    // TODO: matching file names when saving?
}