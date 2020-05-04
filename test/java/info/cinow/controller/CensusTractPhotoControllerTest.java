package info.cinow.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.repository.CensusTractDao;
import info.cinow.repository.CensusTractPhotoDao;
import info.cinow.service.CensusTractPhotoService;
import info.cinow.service.CensusTractService;
import info.cinow.service.PhotoService;

/**
 * CensusTractPhotoControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CensusTractPhotoController.class)
public class CensusTractPhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService service;

    @MockBean
    private CensusTractService censusService;

    @MockBean
    private CensusTractPhotoService censusTractPhotoService;

    @MockBean
    private PhotoMapper<PhotoSaveDto> photoSaveMapper;

    @MockBean
    private PhotoMapper<PhotoDto> photoMapper;

    @Captor
    private ArgumentCaptor<Photo> captor;

    @MockBean
    private CensusTractDao censusTractDao;

    @MockBean
    private CensusTractPhotoDao dao;

    private Photo photo;

    private PhotoSaveDto dto;

    private CensusTract tract;

    @Before
    public void setup() throws IOException, NoDescriptionException, CensusTractDoesNotExistException {
        dto = new PhotoSaveDto();
        dto.setDescription("This is the new description");
        dto.setId(32L);
        dto.setOwnerEmail("owner@email.com");
        dto.setOwnerFirstName("First");
        dto.setOwnerLastName("Last");

        photo = new Photo();
        photo.setApproved(false);
        photo.setDescription("Old description");
        photo.setFileName("photo");
        photo.setId(32L);
        photo.setOwnerEmail("owner@email.com");
        photo.setOwnerFirstName("First");
        photo.setOwnerLastName("Last");

        tract = new CensusTract();
        tract.setGid("1");
        photo.setCensusTract(tract);

        Mockito.when(censusService.getCensusTract(tract.getGid())).thenReturn(tract);
        Mockito.when(service.getPhotoById(photo.getId())).thenReturn(Optional.of(photo));

        Mockito.when(service.updatePhoto(any(Photo.class))).thenReturn(photo);
    }

    @Test
    public void getAllPhotos() throws Exception {
        mvc.perform(get("/census-tracts/{id}/photos", this.tract.getGid()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void photoDescriptionUpdated_RestRemainsTheSame() throws Exception {
        mvc.perform(put("/census-tracts/1/photos/{id}", dto.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isOk());
        photo.setDescription(dto.getDescription());
        Mockito.verify(service).updatePhoto(photo);
    }

    @Test
    public void photoUpdate_NoDescriptionThrowsException() throws Exception {
        Mockito.when(service.updatePhoto(any(Photo.class))).thenThrow(new NoDescriptionException(null));
        mvc.perform(put("/census-tracts/1/photos/{id}", dto.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void photoUpdate_NoTractThrowsException() throws Exception {
        Mockito.when(service.updatePhoto(any(Photo.class))).thenThrow(new CensusTractDoesNotExistException(null));
        mvc.perform(put("/census-tracts/1/photos/{id}", dto.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isUnprocessableEntity());
    }

}