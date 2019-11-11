package info.cinow.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import info.cinow.model.geocodio.GeocodioResponse;

/**
 * GeocodioDao
 */
@Component("geocodioDao")
public class GeocodioDao implements GeocodeDao<GeocodioResponse> {

    private RestTemplate restTemplate;

    private final String URL = "https://api.geocod.io/v1.4/geocode/?q={q}&api_key={api_key}";

    @Value("${geocodio.key}")
    private String geocodioKey;

    @Autowired
    public GeocodioDao(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public GeocodioResponse find(String locationString) {
        Map<String, String> params = new HashMap<String, String>();
        GeocodioResponse response = new GeocodioResponse();
        params.put("api_key", geocodioKey);
        params.put("q", locationString);
        response = this.restTemplate.getForObject(URL, GeocodioResponse.class, params);
        return response;
    }

}