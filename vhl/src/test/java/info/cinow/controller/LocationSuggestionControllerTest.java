package info.cinow.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.jayway.restassured.internal.http.HttpResponseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import info.cinow.model.LocationType;
import info.cinow.service.GeocodeService;
import info.cinow.service.GeocodeServiceImpl;

/**
 * LocationSuggestionControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LocationSuggestController.class)
public class LocationSuggestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GeocodeService service;

    @Test
    public void catch404() throws Exception {
        String place = "van andel arena";
        when(service.getLocationSuggestions(place, LocationType.PLACE)).then(invocation -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        final ResultActions result = mvc.perform(get("/location/place").param("location", place));
        result.andExpect(status().isInternalServerError());
    }

}