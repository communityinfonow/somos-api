package info.cinow.model;

import java.util.Arrays;

public enum LocationType {
    ADDRESS, PLACE;

    public static LocationType fromValue(String value) {
        for (LocationType locationType : values()) {
            if (locationType.name().equalsIgnoreCase(value)) {
                return locationType;
            }
        }
        throw new IllegalArgumentException(
                "Unkown enum type " + value + "; accepted values are " + Arrays.toString(values()));
    }
}