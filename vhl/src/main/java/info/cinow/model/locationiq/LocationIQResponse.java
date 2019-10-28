package info.cinow.model.locationiq;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationIQResponse {

    @JsonIgnoreProperties("results")
    List<LocationIqResult> results;
}