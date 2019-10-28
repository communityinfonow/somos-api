package info.cinow.service;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line
import org.springframework.test.web.client.MockRestServiceServer;

import info.cinow.model.LocationType;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.repository.GeocodeDao;
import info.cinow.dto.LocationSuggestionDto;

/**
 * GeocodeDaoTest
 */
@RunWith(SpringRunner.class)
@RestClientTest(GeocodeService.class)
public class GeocodeServiceImplTest {

    @MockBean
    private GeocodeDao dao;

    @Autowired
    private GeocodeService service;

    @Test
    public void correctDaoIsUsed() {
        Mockito.when(service.getLocationSuggestions("", LocationType.PLACE)).then(invocation -> {
            return new ArrayList<LocationSuggestionDto>();
        });
        service.getLocationSuggestions("", LocationType.PLACE);
        verify(dao).byPlaceName("");
    }
}