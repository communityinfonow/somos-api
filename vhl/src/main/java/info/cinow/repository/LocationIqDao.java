package info.cinow.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import info.cinow.model.locationiq.LocationIqResult;

/**
 * LocationIqDao
 */
@Component("locationIqDao")
public class LocationIqDao implements GeocodeDao<LocationIqResult[]> {

    RestTemplate restTemplate;

    @Value("${locationiq.key}")
    private String locationIqKey;
    private final String URL = "https://us1.locationiq.com/v1/search.php/?q={q}&key={key}&format={format}&viewbox={viewbox}&bounded={bounded}";

    @Autowired
    public LocationIqDao(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public LocationIqResult[] find(String locationString) {
        Map<String, String> params = new HashMap<String, String>();
        LocationIqResult[] response = null;
        params.put("q", locationString);
        params.put("key", locationIqKey);
        params.put("viewbox", "-98.821561,29.777565,-98.096464,29.133046");
        params.put("bounded", "1");
        params.put("format", "json");
        response = this.restTemplate.getForObject(URL, LocationIqResult[].class, params);

        return response;
    }

}