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
public class LocationIqMapper implements LocationSuggestionMapper<LocationIqResult[]> {

    @Override
    public List<LocationSuggestionDto> toDto(LocationIqResult[] results) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        for (LocationIqResult result : results) {
            dto.add(new LocationSuggestionDto(result.getDisplayName(),
                    new Location(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon()))));
        }
        return dto;
    }

}