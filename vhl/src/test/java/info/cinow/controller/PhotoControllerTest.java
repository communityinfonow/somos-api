package info.cinow.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(PhotoController.class)
public class PhotoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService service;

    @Test
    public void givenImage_whenGetCoordinates_ReturnOkayWithCorrectData() throws Exception {
        Photo photo = new Photo();
        photo.setLatitude(1.0);
        photo.setLongitude(2.0);

        Location location = new Location(1.0, 1.0);

        when(service.getGpsCoordinates(anyLong())).thenReturn(location);
        mvc.perform(get("/photos/1/gps-coordinates").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(location.getLat()))
                .andExpect(jsonPath("$.lon").value(location.getLng()));
    }

}