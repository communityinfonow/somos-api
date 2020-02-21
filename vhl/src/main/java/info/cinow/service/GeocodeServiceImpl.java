package info.cinow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.mapper.LocationSuggestionMapper;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.repository.GeocodeDao;

/**
 * GeocodeServiceImpl
 */
@Service
public class GeocodeServiceImpl implements GeocodeService {

    @Autowired
    private GeocodeDao<LocationIqResult> locationIqDao;

    @Override
    public LocationIqResult[] getLocationSuggestionsByAddress(String locationString) {
        return locationIqDao.findByAddress(locationString);
    }

    @Override
    public LocationIqResult getLocationByLatLng(double lat, double lng) {
        return locationIqDao.findByLatLng(lat, lng);
    }

    @Override
    public LocationIqResult[] getLocationSuggestionsByZipCode(String zipCode) {
        return locationIqDao.findByZipCode(zipCode);
    }

}