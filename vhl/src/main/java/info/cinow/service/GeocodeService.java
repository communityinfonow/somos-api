package info.cinow.service;

import info.cinow.model.locationiq.LocationIqResult;

public interface GeocodeService {
    LocationIqResult[] getLocationSuggestions(String locationSearch);
}