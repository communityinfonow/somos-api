package info.cinow.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.multipart.MultipartFile;

import info.cinow.model.LocationType;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.repository.GeocodeDao;
import info.cinow.repository.PhotoDao;
import info.cinow.dto.LocationSuggestionDto;
import info.cinow.dto.PhotoDto;

/**
 * GeocodeDaoTest
 */
@RunWith(SpringRunner.class)
@RestClientTest(PhotoService.class)
public class PhotoServiceImplTest {

    @MockBean
    private PhotoDao dao;

    @Autowired
    private PhotoService service;

    @Test
    // TODO: continue working on this
    public void photoDeletedFromLocalServer() throws Exception {
        MultipartFile file = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        PhotoDto dto = new PhotoDto(1L, "photo", 2L);
        Mockito.when(service.uploadPhotos(new MultipartFile[] { file })).thenReturn(Arrays.asList(dto));

        service.uploadPhotos(new MultipartFile[] { file });
        File savedFile = new File(file.getOriginalFilename());
        assertFalse(savedFile.exists());
    }
}