package info.cinow.dto.mapper;

import java.util.List;

import info.cinow.dto.LocationSuggestionDto;
import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIQResponse;

/**
 * LocationSuggestionMapper
 */
public class LocationSuggestionMapper {
    public static List<LocationSuggestionDto> toLocationSuggestionDto(GeocodioResponse geocodio) {
        // TODO: Map it!
        return null;
    }

    public static List<LocationSuggestionDto> toLocationSuggestionDto(LocationIQResponse locationIQ) {
        // TODO: map it!
        return null;
    }
}