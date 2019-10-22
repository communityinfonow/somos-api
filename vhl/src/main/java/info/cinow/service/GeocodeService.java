package info.cinow.service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;

public interface GeocodeService {
    LocationSuggestionDto getLocationSuggestions(String locationSearch, LocationType locationType);
}