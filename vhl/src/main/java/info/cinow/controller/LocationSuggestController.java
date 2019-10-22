package info.cinow.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.GeocodioResponse;
import info.cinow.model.LocationIQResponse;
import info.cinow.model.LocationType;
import info.cinow.model.LocationTypeConvertor;
import info.cinow.service.GeocodeService;

/**
 * Location suggestions TODO: Unit test
 */
@RestController
@RequestMapping("/location")
public class LocationSuggestController {

    @Autowired
    private GeocodeService geocodeService;

    @RequestMapping("/{locationType}")
    public LocationSuggestionDto geocodio(@RequestParam String locationString,
            @PathVariable LocationType locationType) {
        return geocodeService.getLocationSuggestions(locationString, locationType);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(LocationType.class, new LocationTypeConvertor());
    }

}