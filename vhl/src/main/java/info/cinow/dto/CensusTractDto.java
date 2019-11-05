package info.cinow.dto;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;

import lombok.Data;

/**
 * CensusTractDto
 */
@Data
public class CensusTractDto {

    private Integer id;

    private String tract;

    private MultiPolygon geometry;

}