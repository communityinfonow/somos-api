package info.cinow.dto;

import java.util.List;

import com.vividsolutions.jts.geom.MultiPolygon;

import info.cinow.model.Photo;
import lombok.Data;

/**
 * CensusTractDto
 */
@Data
public class CensusTractDto {

    private String id;

    private MultiPolygon geometry;

    private List<Photo> photos;

}