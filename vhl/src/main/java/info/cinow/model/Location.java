package info.cinow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Double lat;
    private Double lng;

    public Boolean isEmpty() {
        return lat == null && lng == null;
    }
}
