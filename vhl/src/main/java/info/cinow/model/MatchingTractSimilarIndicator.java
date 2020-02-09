package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * MatchingTractSimilarIndicator
 */
@Entity
@Table(name = "matching_tract_similar_indicator")
@Data
public class MatchingTractSimilarIndicator implements MatchingTractIndicator {

    @EmbeddedId
    private MatchingCensusTractsIndicatorId id;

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "parent_tract_gid"), @JoinColumn(name = "child_tract_gid") })
    @MapsId("tractsId")
    private MatchingTract matchingTracts;

    @MapsId("indicatorId")
    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;

    /**
     * The ranking of which to order the similar indicators
     */
    @Column
    private Integer rank;

}