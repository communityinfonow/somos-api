package info.cinow.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.dto.mapper.LocationSuggestionMapper;
import info.cinow.model.LocationType;
import info.cinow.model.LocationTypeConverter;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.service.GeocodeService;
import lombok.extern.slf4j.Slf4j;

/**
 * Location suggestions TODO: Unit test
 */
@Slf4j
@RestController
@RequestMapping("/location-search")
public class LocationSuggestController {

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private LocationSuggestionMapper<LocationIqResult> locationIqMapper;

    /**
     * Catches 400, 401, 403, 429, 500, and restClient errors and passes as 500 to
     * client
     * 
     * @param location
     * @return
     */
    @GetMapping()
    @ResponseBody
    public List<LocationSuggestionDto> getLocationSuggestions(@RequestParam String location) {
        return Arrays.asList(geocodeService.getLocationSuggestionsByAddress(location)).stream()
                .map(locationSuggestion -> locationIqMapper.toDto(locationSuggestion)).collect(Collectors.toList());

    }

    @GetMapping("/zip/{zipCode}")
    @ResponseBody
    public List<LocationSuggestionDto> getLocationSuggestionsByZipCode(@PathVariable("zipCode") String zipCode) {
        return Arrays.asList(geocodeService.getLocationSuggestionsByZipCode(zipCode)).stream()
                .map(locationSuggestion -> locationIqMapper.toDto(locationSuggestion)).collect(Collectors.toList());

    }

    @GetMapping("/latlng/{latlng}")
    public LocationSuggestionDto getLocationByLatLng(@MatrixVariable Map<String, String> latLng) {
        return locationIqMapper.toDto(geocodeService.getLocationByLatLng(Double.parseDouble(latLng.get("lat")),
                Double.parseDouble(latLng.get("lng"))));
    }

    /**
     * Helps serialize enum type TODO
     * 
     * @param webdataBinder
     */
    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(LocationType.class, new LocationTypeConverter());
    }

    /**
     * Catches errors for geocoding apis and throws INTERNAL_SERVER_ERROR
     * 
     * @param e
     */
    @ExceptionHandler(ResponseStatusException.class)
    private void internalServerError(ResponseStatusException e) {
        switch (e.getStatus()) {
            case BAD_REQUEST:
            case UNAUTHORIZED:
            case FORBIDDEN:
            case TOO_MANY_REQUESTS:
                log.error("An error occured accessing the REST APIs for geocoding", e);
                throw new InternalError();
            default:
                break;
        }
    }

    @ExceptionHandler(RestClientException.class)
    private void restClientToInternalServerError(RestClientException e) {
        log.error("An error occured geocoding", e);
        throw new InternalError();
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occured finding location suggestions. If the problem persists, please contact CI:Now")
    public class InternalError extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}