package info.cinow.model.locationiq;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * LocationIqResult
 */
@Data
public class LocationIqResult {

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("licence")
    private String license;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private String osmId;

    private List<String> boundingbox;

    private String lat;

    private String lon;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("class")
    private String theClass;

    private String type;

    private double importance;

    private String icon;

    @JsonProperty("address")
    private AddressDetails addressDetails;

}