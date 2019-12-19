package info.cinow.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import info.cinow.dto.PhotoAdminDto;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoAdminMapper;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.dto.mapper.PhotoMapperImpl;
import info.cinow.dto.mapper.PhotoSaveMapper;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.CensusTract;
import info.cinow.model.Location;
import info.cinow.model.Photo;
import info.cinow.repository.PhotoDao;
import info.cinow.service.PhotoService;

/**
 * LocationSuggestionControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhotoController.class)
public class PhotoControllerTest {

    @TestConfiguration
    static class PhotoMapperTestContextConfig {

        @Bean
        public PhotoMapper<PhotoDto> photoMapper() {
            return new PhotoMapperImpl();
        }

        @Bean
        public PhotoMapper<PhotoSaveDto> photoSaveMapper() {
            return new PhotoSaveMapper();
        }

        @Bean
        public PhotoMapper<PhotoAdminDto> photoAdminMapper() {
            return new PhotoAdminMapper();
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService service;

    @MockBean
    private PhotoDao dao;

    private Location location;

    private Photo photo;

    private MockMultipartFile mockFile;

    @Before
    public void setup() throws FileNotFoundException, IOException, ImageTooLargeException, ImageNameTooLongException,
            WrongFileTypeException {
        photo = new Photo();
        photo.setLatitude(1.0);
        photo.setLongitude(2.0);
        photo.setId(1L);
        CensusTract tract = new CensusTract();
        tract.setGid(1);
        photo.setCensusTract(tract);

        location = new Location(1.0, 1.0);
        Mockito.when(service.getGpsCoordinates(anyLong())).thenReturn(Optional.of(location));
        Mockito.when(service.getPhotoById(photo.getId())).thenReturn(Optional.of(photo));
        Mockito.when(service.uploadPhoto(any(MultipartFile.class))).thenReturn(photo);
        Mockito.when(dao.save(any(Photo.class))).thenReturn(photo);

        this.mockFile = new MockMultipartFile("photo", "fileThatDoesNotExists.jpeg", "image/jpeg", new FileInputStream(
                new File("visualizing-healthy-lives-api/vhl/src/test/resources/Photo Upload Screen 3.png")));
    }

    @Test
    public void getPhotos() throws Exception {
        // TODO
        mvc.perform(get("/photos").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getPhoto() throws Exception {
        mvc.perform(get("/photos/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(photo.getId()));

    }

    @Test
    public void savePhotos() throws Exception {
        mvc.perform(multipart("/photos").file(this.mockFile).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenImage_whenGetCoordinates_ReturnOkayWithCorrectData() throws Exception {

        mvc.perform(get("/photos/1/gps-coordinates").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(location.getLat()))
                .andExpect(jsonPath("$.lng").value(location.getLng()));

        Mockito.verify(service).getGpsCoordinates(1L);

    }

    @Test
    public void savePhotoWithLargeName_ThrowError() throws Exception {
        Mockito.when(service.uploadPhoto(any(MultipartFile.class))).thenThrow(new ImageNameTooLongException(0));
        mvc.perform(multipart("/photos").file(this.mockFile).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void savePhotoWithLargeSize_ThrowError() throws Exception {
        Mockito.when(service.uploadPhoto(any(MultipartFile.class))).thenThrow(new ImageTooLargeException(null));
        mvc.perform(multipart("/photos").file(this.mockFile).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPayloadTooLarge());
    }

    @Test
    public void savePhotoWithWrongType_ThrowError() throws Exception {
        Mockito.when(service.uploadPhoto(any(MultipartFile.class))).thenThrow(new WrongFileTypeException());
        mvc.perform(multipart("/photos").file(this.mockFile).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }

}