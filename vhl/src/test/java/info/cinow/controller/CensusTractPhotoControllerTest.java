package info.cinow.controller;

import java.io.IOException;
import java.util.Optional;

import javax.net.ssl.SSLEngineResult.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.repository.PhotoDao;
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
    public void setup() throws IOException {
        dto = new PhotoSaveDto();
        dto.setApproved(false);
        dto.setDescription("This is the new description");
        dto.setFileName("photo");
        dto.setFilePathName("photo.png");
        dto.setId(32L);
        dto.setOwnerEmail("owner@email.com");
        dto.setOwnerFirstName("First");
        dto.setOwnerLastName("Last");

        photo = new Photo();
        photo.setApproved(false);
        photo.setDescription("Old description");
        photo.setFileName("photo");
        photo.setImageRepositoryPath("photo.png");
        photo.setId(32L);
        photo.setOwnerEmail("owner@email.com");
        photo.setOwnerFirstName("First");
        photo.setOwnerLastName("Last");
        photo.setLatitude(1.0);
        CensusTract tract = new CensusTract();
        tract.setGid(1);
        photo.setCensusTract(tract);

        Mockito.when(censusService.getCensusTract(tract.getGid())).thenReturn(tract);
        Mockito.when(service.getPhoto(photo.getId())).thenReturn(Optional.of(photo));

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

}