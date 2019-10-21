package info.cinow.service;

import info.cinow.dto.LocationSuggestionDto;

public interface GeocodeService {
    LocationSuggestionDto getLocationSuggestions(String locationSearch);
}

// TODO: extend functionality twice for both geocode services