package info.cinow.controller;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.dto.PhotoSaveDto;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.service.CensusTractService;
import info.cinow.service.PhotoService;

/**
 * CensusTractPhotoControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CensusTractPhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService service;

    @MockBean
    private CensusTractService censusService;

    @Captor
    ArgumentCaptor<Photo> captor;

    Photo photo;

    PhotoSaveDto dto;

    @Before
    public void setup() throws IOException, NoDescriptionException, CensusTractDoesNotExistException {
        dto = new PhotoSaveDto();
        dto.setApproved(false);
        dto.setDescription("This is the new description");
        dto.setFileName("photo");
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

        CensusTract tract = new CensusTract();
        tract.setGid(1);
        photo.setCensusTract(tract);

        Mockito.when(censusService.getCensusTract(tract.getGid())).thenReturn(tract);
        Mockito.when(service.getPhotoById(photo.getId())).thenReturn(Optional.of(photo));

        Mockito.when(service.updatePhoto(any(Photo.class))).thenReturn(photo);
    }

    // TODO: add basic testing for all endpoints

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