package info.cinow.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

/**
 * MatchingCensusTracts
 */
@Entity(name = "matchingTract")
@Table(name = "matching_tract")
@Data
public class MatchingTract {

    @EmbeddedId
    private MatchingCensusTractsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("parentTractId")
    private CensusTract parentTract;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("childTractId")
    private CensusTract childTract;

    @Column(name = "miles_difference")
    private Double milesDifference;

    @Column(name = "life_expectancy_difference")
    private Double lifeExpentancyDifference;

    /**
     * The ranking order for which to display this matching tract
     */
    @Column
    private Integer rank;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MatchingTract that = (MatchingTract) o;
        return Objects.equals(parentTract, that.parentTract) && Objects.equals(childTract, that.childTract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentTract, childTract);
    }

}