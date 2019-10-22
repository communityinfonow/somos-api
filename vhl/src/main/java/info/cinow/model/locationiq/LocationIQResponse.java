package info.cinow.model.locationiq;

import java.util.List;

import lombok.Data;

@Data
public class LocationIQResponse {
    // TODO: ignore variable name somehow??
    List<LocationIqResult> results;
}