package info.cinow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.MultiPolygon;

import lombok.Data;

/**
 * Tract
 */
@Data
@Entity
@Table(name = "bexartracts_2010")
public class CensusTract implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id()
    private Integer gid;

    @Column
    private String tract;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    @Column(name = "geom", columnDefinition = "Geometry")
    private MultiPolygon geometry;

}