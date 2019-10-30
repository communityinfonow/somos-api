package info.cinow.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.Location;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;

/**
 * LocationSuggestionMapper
 */
public class LocationSuggestionMapper {

    public static List<LocationSuggestionDto> toLocationSuggestionDto(GeocodioResponse geocodioResponse) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        geocodioResponse.getResults().forEach((result) -> {
            dto.add(new LocationSuggestionDto(result.getFormattedAddress(), result.getLocation()));
        });
        return dto;
    }

    public static List<LocationSuggestionDto> toLocationSuggestionDto(LocationIqResult[] results) {
        List<LocationSuggestionDto> dto = new ArrayList<LocationSuggestionDto>();
        for (LocationIqResult result : results) {
            dto.add(new LocationSuggestionDto(result.getDisplayName(),
                    new Location(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon()))));
        }
        return dto;
    }
}