package info.cinow.model;

import lombok.Data;

/**
 * A result from the Geocodio response.
 */
@Data
public class Result {

    private AddressComponents addressComponents;
    private String formatted_address;
    private Location location;
    private int accuracy;
    private String accuracy_type;
    private String source;
}
