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
public class LocationIqDao implements GeocodeDao<LocationIqResult> {

    RestTemplate restTemplate;

    @Value("${locationiq.key}")
    private String locationIqKey;
    private final String URL = "https://us1.locationiq.com/v1/search.php/?q={q}&key={key}&format={format}&viewbox={viewbox}&bounded={bounded}&addressdetails={addressdetails}";
    private final String ZIP_URL = "https://us1.locationiq.com/v1/search.php?postalcode={postalcode}&key={key}&format={format}&viewbox={viewbox}&bounded={bounded}&addressdetails={addressdetails}&countrycodes={countrycodes}";
    private final String REVERSE_LOOKUP_URL = "https://us1.locationiq.com/v1/reverse.php/?&key={key}&lat={lat}&lon={lon}&format={format}&addressdetails={addressdetails}";

    @Autowired
    public LocationIqDao(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public LocationIqResult[] findByAddress(String locationString) {
        Map<String, String> params = new HashMap<String, String>();
        LocationIqResult[] response = null;
        params.put("q", locationString);
        params.put("key", locationIqKey);
        params.put("viewbox", "-98.821561,29.777565,-98.096464,29.133046");
        params.put("bounded", "1");
        params.put("format", "json");
        params.put("addressdetails", "1");
        response = this.restTemplate.getForObject(this.URL, LocationIqResult[].class, params);

        return response;
    }

    public LocationIqResult[] findByZipCode(String zipcode) {
        Map<String, String> params = new HashMap<String, String>();
        LocationIqResult[] response = null;
        params.put("postalcode", zipcode);
        params.put("key", locationIqKey);
        params.put("viewbox", "-98.821561,29.777565,-98.096464,29.133046");
        params.put("bounded", "1");
        params.put("format", "json");
        params.put("addressdetails", "1");
        params.put("countrycodes", "us");
        response = this.restTemplate.getForObject(this.ZIP_URL, LocationIqResult[].class, params);

        return response;
    }

    public LocationIqResult findByLatLng(double lat, double lng) {
        Map<String, String> params = new HashMap<String, String>();
        LocationIqResult response = null;

        params.put("key", locationIqKey);
        params.put("lat", lat + "");
        params.put("lon", lng + "");
        params.put("format", "json");
        params.put("addressdetails", "1");
        response = this.restTemplate.getForObject(this.REVERSE_LOOKUP_URL, LocationIqResult.class, params);
        return response;
    }

}