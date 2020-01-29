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
    private GeocodeDao<LocationIqResult[]> locationIqDao;

    @Override
    public LocationIqResult[] getLocationSuggestions(String locationString) {
        return locationIqDao.find(locationString);
    }

}