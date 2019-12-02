package info.cinow.controller;

import static org.mockito.ArgumentMatchers.anyLong;

import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.model.Location;
import info.cinow.model.Photo;
import info.cinow.service.PhotoService;

/**
 * LocationSuggestionControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService service;

    private Location location;

    @Before
    public void setup() {
        Photo photo = new Photo();
        photo.setLatitude(1.0);
        photo.setLongitude(2.0);

        location = new Location(1.0, 1.0);
        Mockito.when(service.getGpsCoordinates(anyLong())).thenReturn(Optional.of(location));
    }

    @Test
    public void givenImage_whenGetCoordinates_ReturnOkayWithCorrectData() throws Exception {

        mvc.perform(get("/photos/1/gps-coordinates").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(location.getLat()))
                .andExpect(jsonPath("$.lng").value(location.getLng()));

        Mockito.verify(service).getGpsCoordinates(1L);

    }

    // TODO: all endpoints

}