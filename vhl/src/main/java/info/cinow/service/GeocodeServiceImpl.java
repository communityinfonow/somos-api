package info.cinow.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIQResponse;
import info.cinow.model.LocationType;
import info.cinow.repository.GeocodeDao;

/**
 * GeocodeServiceImpl
 */
@Service
public class GeocodeServiceImpl implements GeocodeService {

    private GeocodeDao geocodeDao;

    private ModelMapper modelMapper;

    public GeocodeServiceImpl() {
        this.geocodeDao = new GeocodeDao();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public LocationSuggestionDto getLocationSuggestions(String locationString, LocationType locationType) {
        return this.determineGeocodeDao(locationString, locationType);
    }

    private LocationSuggestionDto determineGeocodeDao(String locationString, LocationType locationType) {
        return locationType.equals(LocationType.ADDRESS) ? convertToDto(geocodeDao.geocodio(locationString))
                : convertToDto(geocodeDao.locationIq(locationString));
    }

    private LocationSuggestionDto convertToDto(GeocodioResponse geocodioResponse) {
        return this.modelMapper.map(geocodioResponse, LocationSuggestionDto.class);
    }

    private LocationSuggestionDto convertToDto(LocationIQResponse geocodioResponse) {
        return this.modelMapper.map(geocodioResponse, LocationSuggestionDto.class);
    }

}