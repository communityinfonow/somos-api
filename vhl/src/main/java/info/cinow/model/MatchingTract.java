package info.cinow.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * MatchingCensusTracts
 */
@Entity(name = "matchingTract")
@Table(name = "matching_tract")
@Data
public class MatchingTract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    // @OneToMany(fetch = FetchType.LAZY)
    // @JoinColumn(name = "matchingTract")
    // private List<MatchingTractDissimilarIndicator> dissimilarIndicators;

    // @OneToMany(fetch = FetchType.LAZY)
    // @JoinColumn(name = "matchingTract")
    // private List<MatchingTractSimilarIndicator> similarIndicators;

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