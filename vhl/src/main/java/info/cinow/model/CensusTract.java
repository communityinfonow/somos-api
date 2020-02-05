package info.cinow.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.MultiPolygon;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

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

    @OneToMany(mappedBy = "censusTract")
    private List<Photo> photos;

    // @OneToMany(mappedBy = "childTract")
    // private List<MatchingTract> parentMatchingCensusTract;

    // @OneToMany(mappedBy = "parentTract")
    // private List<MatchingTract> matchingCensusTracts;

}