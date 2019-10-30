package info.cinow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.dto.mapper.LocationSuggestionMapper;
import info.cinow.model.LocationType;
import info.cinow.repository.GeocodeDao;

/**
 * GeocodeServiceImpl
 */
@Service
public class GeocodeServiceImpl implements GeocodeService {

    @Autowired
    private GeocodeDao geocodeDao;

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
        return locationType.equals(LocationType.ADDRESS)
                ? LocationSuggestionMapper.toLocationSuggestionDto(geocodeDao.byAddress(locationString))
                : LocationSuggestionMapper.toLocationSuggestionDto(geocodeDao.byPlaceName(locationString));
    }

}