package info.cinow.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line

import info.cinow.dto.PhotoDto;
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

    private MockMultipartFile[] files;

    @Before
    public void setup() throws Exception {

        PhotoDto dto = new PhotoDto(1L, "photo");

        Photo returnPhoto = new Photo();
        returnPhoto.setFileName("photo");
        returnPhoto.setId(1L);

        Mockito.when(photoDao.save(any(Photo.class))).thenReturn(returnPhoto);

    }

    @Test
    public void photoDeletedFromLocalServer() throws Exception {

        MockMultipartFile[] files = new MockMultipartFile[1];
        files[0] = new MockMultipartFile("fileThatDoesNotExists.jpeg", "fileThatDoesNotExists.jpeg", "image/jpeg",
                new FileInputStream(
                        new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));
        service.uploadPhotos(files);
        File savedFile = new File(files[0].getOriginalFilename());
        assertFalse(savedFile.exists());
    }

    // TODO: what if there isn't a file name?

    // TODO: matching file names when saving?
}