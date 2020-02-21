package info.cinow.model;

import java.util.Set;

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

@Entity
@Data
@Table(name = "bexartracts_2010")
public class CensusTract {

    public CensusTract() {

    }

    public CensusTract(String gid) {
        this.gid = gid;
    }

    @Id
    private String gid;

    @Column(name = "geom", columnDefinition = "Geometry")
    private MultiPolygon polygon;

}