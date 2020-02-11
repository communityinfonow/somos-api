package info.cinow.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @ManyToOne
    @JoinColumn(name = "parent_tract_gid", referencedColumnName = "gid")
    private CensusTract parentTract;

    @ManyToOne
    @JoinColumn(name = "child_tract_gid", referencedColumnName = "gid")
    private CensusTract childTract;

    @Column(name = "miles_difference")
    private Double milesDifference;

    @Column(name = "life_expectancy_difference")
    private Double lifeExpentancyDifference;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id.matchingTracts")
    private Set<MatchingTractSimilarIndicator> similarIndicators;

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "matching_tract_dissimilar_indicator", joinColumns = {
    // @JoinColumn(name = "child_tract_gid", referencedColumnName =
    // "child_tract_gid"),
    // @JoinColumn(name = "parent_tract_gid", referencedColumnName =
    // "parent_tract_gid") }, inverseJoinColumns = {
    // @JoinColumn(name = "indicator_id", referencedColumnName = "id") })
    // private Set<Indicator> dissimilarIndicators;

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