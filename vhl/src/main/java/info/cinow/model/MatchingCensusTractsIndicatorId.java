package info.cinow.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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

    @Column(name = "match_id")
    private String matchId;

    @Column(name = "indicator_id")
    private Long indicatorId;

    public MatchingCensusTractsIndicatorId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MatchingCensusTractsIndicatorId that = (MatchingCensusTractsIndicatorId) o;
        return Objects.equals(matchId, that.matchId) && Objects.equals(indicatorId, that.indicatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, indicatorId);
    }

}