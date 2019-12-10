package info.cinow.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.MultiPolygon;

import lombok.Data;

/**
 * Tract
 */

@Entity
@Table(name = "bexartracts_2010")
@Data
public class CensusTract {

    @Id()
    private Integer gid;

    @Column
    private String tract;

    @Column(name = "geom", columnDefinition = "Geometry")
    private MultiPolygon polygon;

    @OneToMany(mappedBy = "censusTract", fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "childTract", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingCensusTracts> parentMatchingCensusTract;

    @OneToMany(mappedBy = "parentTract", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingCensusTracts> matchingCensusTracts;

    @OneToOne(mappedBy = "censusTract", fetch = FetchType.LAZY)
    private CensusTractData data;

}