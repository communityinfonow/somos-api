package info.cinow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.dto.mapper.LocationSuggestionMapper;
import info.cinow.model.CensusTract;
import info.cinow.model.LocationType;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;
import info.cinow.repository.CensusTractDao;
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

    @Autowired
    private CensusTractDao censusTractDao;

    @Override
    public List<LocationSuggestionDto> getLocationSuggestions(String locationString, LocationType locationType) {
        return this.determineGeocodeDao(locationString, locationType);
    }

    private boolean isLocationWithinCensusTracts(double longitude, double latitude) {
        return this.censusTractDao.getContainingTract(longitude, latitude).isPresent();
    }

    /**
     * Determines the dao based on location type.
     * 
     * Address search with geocodio due to accuracy, place with locationIQ for its
     * handling of place search
     */
    private List<LocationSuggestionDto> determineGeocodeDao(String locationString, LocationType locationType) {
        return locationType.equals(LocationType.ADDRESS) ? this.geocodioResults(locationString)
                : locationIqResults(locationString);
    }

    private List<LocationSuggestionDto> geocodioResults(String locationString) {
        GeocodioResponse response = geocodioDao.find(locationString);
        response.setResults(response.getResults().stream().filter(result -> this
                .isLocationWithinCensusTracts(result.getLocation().getLng(), result.getLocation().getLat()))
                .collect(Collectors.toList()));
        return geocodioMapper.toDto(response);
    }

    private List<LocationSuggestionDto> locationIqResults(String locationString) {
        return locationIqMapper.toDto(locationIqDao.find(locationString));
    }

}