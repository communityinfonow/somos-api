package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.postgis.*;

import lombok.Data;

/**
 * Tract
 */
@Data
@Entity
@Table(name = "bexartracts_2010")
public class CensusTract {

    @Id()
    private Integer gid;

    @Column
    private String tract;

    // @JsonSerialize(using = GeometrySerializer.class)
    @Column(name = "geom", columnDefinition = "Geometry")
    private Geometry geometry;

}