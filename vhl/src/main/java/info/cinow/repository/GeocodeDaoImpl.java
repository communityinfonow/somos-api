package info.cinow.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;
import lombok.extern.slf4j.Slf4j;

/**
 * GeocodeDao
 * 
 */
@Slf4j
@Repository
public class GeocodeDaoImpl implements GeocodeDao {

    private RestTemplate restTemplate;

    private final String geocodioUrl = "https://api.geocod.io/v1.4/geocode/?q={q}&api_key={api_key}";

    private final String locationIqUrl = "https://us1.locationiq.com/v1/search.php/?q={q}&key={key}&format={format}";

    @Value("${geocodio.key}")
    private String geocodioKey;

    @Value("${locationiq.key}")
    private String locationIqKey;

    public GeocodeDaoImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public GeocodioResponse byAddress(String locationString) {
        Map<String, String> params = new HashMap<String, String>();
        GeocodioResponse response = new GeocodioResponse();
        params.put("api_key", geocodioKey);
        params.put("q", locationString);
        response = this.restTemplate.getForObject(geocodioUrl, GeocodioResponse.class, params);

        return response;
    }

    public LocationIqResult[] byPlaceName(String locationString) {
        Map<String, String> params = new HashMap<String, String>();
        LocationIqResult[] response = null;
        params.put("q", locationString);
        params.put("key", locationIqKey);
        params.put("format", "json");
        response = this.restTemplate.getForObject(locationIqUrl, LocationIqResult[].class, params);

        return response;
    }
}
