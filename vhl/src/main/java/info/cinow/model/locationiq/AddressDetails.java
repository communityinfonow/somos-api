package info.cinow.model.locationiq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddressDetails {

    @JsonProperty("house_number")
    private String houseNumber;

    private String road;

    private String neighbourhood;

    private String hamlet;

    private String suburb;

    private String village;

    private String town;

    @JsonProperty("city_district")
    private String cityDistrict;

    private String city;

    private String region;

    private String county;

    @JsonProperty("state_district")
    private String stateDistrict;

    private String state;

    private String postcode;

    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    private String name;

    private String pedestrian;
}
