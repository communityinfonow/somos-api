package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.Location;
import info.cinow.model.LocationType;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;
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
        return locationType.equals(LocationType.ADDRESS) ? convertToDto(geocodeDao.byAddress(locationString))
                : convertToDto(geocodeDao.byPlaceName(locationString));
    }

    private List<LocationSuggestionDto> convertToDto(GeocodioResponse geocodioResponse) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        geocodioResponse.getResults().forEach((result) -> {
            dto.add(new LocationSuggestionDto(result.getFormattedAddress(), result.getLocation()));
        });
        return dto;
    }

    private List<LocationSuggestionDto> convertToDto(LocationIqResult[] results) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        for (LocationIqResult result : results) {
            dto.add(new LocationSuggestionDto(result.getDisplayName(), new Location(result.getLat(), result.getLon())));
        }
        return dto;
    }
}