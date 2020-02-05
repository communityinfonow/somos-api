package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    @MapsId("indicatorId")
    private Indicator indicator;

    @ManyToOne
    @MapsId("gid")
    private CensusTract censusTract;

    @Column
    private Double value;

    @Embedded
    private MarginOfError marginOfError;

}