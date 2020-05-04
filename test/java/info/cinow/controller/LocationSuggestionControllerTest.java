package info.cinow.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

}