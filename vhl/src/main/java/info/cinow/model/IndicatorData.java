package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

/**
 * CensusTractData
 */
@Entity
@Table(name = "indicator_data")
@Data
public class IndicatorData {

    @EmbeddedId
    private IndicatorTractId id;

    @ManyToOne
    @JoinColumn(name = "indicator_id", referencedColumnName = "id")
    @MapsId("indicatorId")
    private Indicator indicator;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "census_tract_id", referencedColumnName = "gid")
    @MapsId("gid")
    private CensusTract censusTract;

    @Column
    private Double value;

    @Transient
    private Double maxValue;

    @Embedded
    private MarginOfError marginOfError;

}