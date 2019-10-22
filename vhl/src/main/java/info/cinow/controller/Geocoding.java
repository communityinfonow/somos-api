package info.cinow.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;

/**
 * Geocoding
 */
@RestController
@RequestMapping("/geocode")
public class Geocoding {

    @RequestMapping("/{locationType}")
    public LocationSuggestionDto geocodio(@RequestParam String locationString,
            @PathVariable LocationType locationType) {
        return null;
        // TODO
    }

}