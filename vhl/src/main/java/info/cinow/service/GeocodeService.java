package info.cinow.service;

import info.cinow.model.locationiq.LocationIqResult;

public interface GeocodeService {
    LocationIqResult[] getLocationSuggestionsByAddress(String locationSearch);

    LocationIqResult getLocationByLatLng(double lat, double lng);

    LocationIqResult[] getLocationSuggestionsByZipCode(String zipCode);
}