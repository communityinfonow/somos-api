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
public class MatchingCensusTractsId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "parent_id")
    private Long parentTractId;

    @Column(name = "child_id")
    private Long childTractId;

    public MatchingCensusTractsId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MatchingCensusTractsId that = (MatchingCensusTractsId) o;
        return Objects.equals(parentTractId, that.parentTractId) && Objects.equals(childTractId, that.childTractId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentTractId, childTractId);
    }

}