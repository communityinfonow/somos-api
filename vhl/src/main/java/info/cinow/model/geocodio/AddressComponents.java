package info.cinow.model.geocodio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

/**
 * The components of an address for Geocodio.
 */
@Data
public class AddressComponents {

    /**
     * The address number.
     */
    private String number;

    /**
     * The direction of the street address.
     */
    private String predirectional;

    /**
     * The street.
     */
    private String street;

    /**
     * The suffix or address type.
     */
    private String suffix;

    /**
     * The street address, appended and formatted.
     */
    @JsonProperty("formatted_street")
    private String formattedStreet;

    /**
     * The city.
     */
    private String city;

    /**
     * The state.
     */
    private String state;

    /**
     * The zip code.
     */
    private String zip;

    /**
     * The country.
     */
    private String country;
}
