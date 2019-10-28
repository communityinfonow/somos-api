package info.cinow.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;
import lombok.extern.slf4j.Slf4j;

/**
 * GeocodeDao TODO: unit tests - test with wrong api key, no api key, not
 * encoded string, etc. Also test with errors from the api such as not having a
 * postal code or city TODO: make this implement an interface
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
        Map<String, String> params = null;
        GeocodioResponse response = new GeocodioResponse();

        params = Map.of("api_key", geocodioKey, "q", locationString);
        response = this.restTemplate.getForObject(geocodioUrl, GeocodioResponse.class, params);

        return response;
    }

    public LocationIqResult[] byPlaceName(String locationString) {
        Map<String, String> params = null;
        LocationIqResult[] response = null;

        params = Map.of("q", locationString, "format", "json", "key", locationIqKey);
        response = this.restTemplate.getForObject(locationIqUrl, LocationIqResult[].class, params);

        return response;
    }
}
