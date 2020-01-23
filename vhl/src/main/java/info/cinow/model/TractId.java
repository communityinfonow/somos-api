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
public class TractId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "gid")
    private Integer gid;

    public TractId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TractId that = (TractId) o;
        return Objects.equals(gid, that.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid);
    }

}