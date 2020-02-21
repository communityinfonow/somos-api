package info.cinow.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.Location;
import info.cinow.model.locationiq.LocationIqResult;

/**
 * LocationIqMapper
 */
@Component("locationIqMapper")
public class LocationIqMapper implements LocationSuggestionMapper<LocationIqResult> {

    @Override
    public LocationSuggestionDto toDto(LocationIqResult result) {

        return new LocationSuggestionDto(result.getDisplayName(),
                new Location(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon())),
                result.getAddressDetails());

    }

}