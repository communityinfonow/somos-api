package info.cinow.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * MatchingCensusTractsId
 */
@Embeddable
@Data
public class MatchingCensusTractsIndicatorId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonBackReference
    private MatchingTract matchingTracts;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;

    public MatchingCensusTractsIndicatorId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MatchingCensusTractsIndicatorId that = (MatchingCensusTractsIndicatorId) o;
        return Objects.equals(matchingTracts.getId(), that.matchingTracts.getId())
                && Objects.equals(indicator.getId(), that.indicator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchingTracts.getId(), indicator.getId());
    }

}