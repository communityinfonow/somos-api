package info.cinow.dto;

import info.cinow.model.Location;
import lombok.Data;

@Data
public class LocationSuggestionDto {

    /**
     * The formatted address of the suggestion.
     */
    String formattedAddress;

    /**
     * The location of the suggestions.
     */
    Location location;
}
