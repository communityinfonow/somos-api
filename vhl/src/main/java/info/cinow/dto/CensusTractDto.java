package info.cinow.dto;

import java.util.List;

import com.vividsolutions.jts.geom.MultiPolygon;

import info.cinow.model.CensusTractData;
import info.cinow.model.Photo;
import lombok.Data;

/**
 * CensusTractDto
 */
@Data
public class CensusTractDto {

    private Integer id;

    private String tract;

    private MultiPolygon geometry;

    private List<Photo> photos;

    private CensusTractData data;

}