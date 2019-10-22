package info.cinow.model;

import java.util.List;

import lombok.Data;

/**
 * GeocodioResponse
 */
@Data
public class GeocodioResponse {

    Input input;

    List<Result> results;
}