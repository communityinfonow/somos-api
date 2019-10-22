package info.cinow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A result from the Geocodio response.
 */
@Data
public class Result {

    private AddressComponents addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;
    private Location location;
    private int accuracy;
    @JsonProperty("accuracy_type")
    private String accuracyType;
    private String source;
}
