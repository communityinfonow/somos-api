package info.cinow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.model.LocationType;
import info.cinow.service.GeocodeService;

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
        final ResultActions result = mvc.perform(get("/place").param("location", place));
        result.andExpect(status().isInternalServerError());
    }

}