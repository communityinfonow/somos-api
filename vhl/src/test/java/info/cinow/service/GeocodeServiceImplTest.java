package info.cinow.service;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;
import info.cinow.repository.GeocodeDao;

/**
 * GeocodeDaoTest
 */
@RunWith(SpringRunner.class)
@RestClientTest(GeocodeService.class)
public class GeocodeServiceImplTest {

    @Autowired
    private GeocodeService service;

    @Test
    public void correctDaoIsUsed() {
        Mockito.when(service.getLocationSuggestions("", LocationType.PLACE)).then(invocation -> {
            return new ArrayList<LocationSuggestionDto>();
        });
        service.getLocationSuggestions("", LocationType.PLACE);
        // TODO
    }
}