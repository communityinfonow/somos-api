package info.cinow.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.Location;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;

/**
 * LocationSuggestionMapper
 */
@Component("geocodioMapper")
public class GeocodioMapper implements LocationSuggestionMapper<GeocodioResponse> {

    @Override
    public List<LocationSuggestionDto> toDto(GeocodioResponse model) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        model.getResults().forEach((result) -> {
            dto.add(new LocationSuggestionDto(result.getFormattedAddress(), result.getLocation()));
        });
        return dto;
    }
}