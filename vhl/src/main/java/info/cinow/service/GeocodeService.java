package info.cinow.service;

import java.util.List;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.LocationType;

public interface GeocodeService {
    List<LocationSuggestionDto> getLocationSuggestions(String locationSearch, LocationType locationType);
}