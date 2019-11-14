package info.cinow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.dto.mapper.LocationSuggestionMapper;
import info.cinow.model.LocationType;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.repository.GeocodeDao;

/**
 * GeocodeServiceImpl TODO don't return DTO, return the real object here
 */
@Service
public class GeocodeServiceImpl implements GeocodeService {

    @Autowired
    private GeocodeDao<GeocodioResponse> geocodioDao;

    @Autowired
    private GeocodeDao<LocationIqResult[]> locationIqDao;

    @Autowired
    private LocationSuggestionMapper<GeocodioResponse> geocodioMapper;

    @Autowired
    private LocationSuggestionMapper<LocationIqResult[]> locationIqMapper;

    @Override
    public List<LocationSuggestionDto> getLocationSuggestions(String locationString, LocationType locationType) {
        return this.determineGeocodeDao(locationString, locationType);
    }

    /**
     * Determines the dao based on location type.
     * 
     * Address search with geocodio due to accuracy, place with locationIQ for its
     * handling of place search
     */
    private List<LocationSuggestionDto> determineGeocodeDao(String locationString, LocationType locationType) {
        return locationType.equals(LocationType.ADDRESS) ? geocodioMapper.toDto(geocodioDao.find(locationString))
                : locationIqMapper.toDto(locationIqDao.find(locationString));
    }

}