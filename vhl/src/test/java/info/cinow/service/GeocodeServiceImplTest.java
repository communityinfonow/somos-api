package info.cinow.service;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;

/**
 * GeocodeDaoTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeocodeServiceImplTest {

    @MockBean
    private GeocodeService geocodeService;

}