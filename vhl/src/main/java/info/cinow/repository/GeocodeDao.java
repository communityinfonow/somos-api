package info.cinow.repository;

import org.springframework.web.client.RestTemplate;

import info.cinow.model.GeocodioResponse;
import info.cinow.model.LocationIQResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * GeocodeDao TODO: unit tests
 */
@Slf4j
public class GeocodeDao {

    private RestTemplate restTemplate;

    public GeocodeDao() {
        this.restTemplate = new RestTemplate();
    }

    public GeocodioResponse geocodio(String locationString) {
        GeocodioResponse response = null;
        try {
            response = this.restTemplate.getForObject("url", GeocodioResponse.class);
        } catch (Exception e) {
            log.error("An error occured geocoding with geocodio", e);
        }
        return response;
    }

    public LocationIQResponse locationIq(String locationString) {
        try {
            LocationIQResponse response = this.restTemplate.getForObject("url", LocationIQResponse.class);
        } catch (Exception e) {
            log.error("An error occured gecoding with locationIq", e);
        }

    }
}