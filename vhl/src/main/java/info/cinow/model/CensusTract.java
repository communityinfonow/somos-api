package info.cinow.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.MultiPolygon;

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

    @Column(name = "geom", columnDefinition = "Geometry")
    private MultiPolygon polygon;

    @OneToMany(mappedBy = "tract")
    private List<Photo> photos;

}