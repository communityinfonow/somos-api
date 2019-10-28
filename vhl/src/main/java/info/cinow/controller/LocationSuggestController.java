package info.cinow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;
import info.cinow.model.LocationTypeConvertor;
import info.cinow.service.GeocodeService;
import lombok.extern.slf4j.Slf4j;

/**
 * Location suggestions TODO: Unit test
 */
@Slf4j
@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:8080")
public class LocationSuggestController {

    @Autowired
    private GeocodeService geocodeService;

    /**
     * Catches 400, 401, 403, 429, 500, and restClient errors and passes as 500 to
     * client
     * 
     * @param location
     * @param locationType
     * @return
     */
    @RequestMapping(value = "/{locationType}", method = RequestMethod.GET)
    @ResponseBody
    public List<LocationSuggestionDto> getLocationSuggestions(@RequestParam String location,
            @PathVariable LocationType locationType) {
        List<LocationSuggestionDto> response = new ArrayList<LocationSuggestionDto>();

        response = geocodeService.getLocationSuggestions(location, locationType);

        return response;
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(LocationType.class, new LocationTypeConvertor());
    }

    /**
     * Catches errors for geocoding apis and throws INTERNAL_SERVER_ERROR
     * 
     * @param e
     */
    @ExceptionHandler(ResponseStatusException.class)
    private void internalServerError(ResponseStatusException e) {
        switch (e.getStatus()) {
        // case HttpStatus.NOT_FOUND ->
        case BAD_REQUEST:
        case UNAUTHORIZED:
        case FORBIDDEN:
        case TOO_MANY_REQUESTS:
            log.error("An error occured accessing the REST APIs for geocoding", e);
            throw new InternalError();
        }
    }

    @ExceptionHandler(RestClientException.class)
    private void restClientToInternalServerError(RestClientException e) {
        log.error("An error occured geocoding", e);
        throw new InternalError();
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occured finding location suggestions. If the problem persists, please contact CI:Now")
    public class InternalError extends RuntimeException {
    }
}