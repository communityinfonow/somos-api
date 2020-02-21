package info.cinow.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * IndicatorTractId
 */
@Embeddable
@Data
public class IndicatorTractId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "indicator_id")
    private Long indicatorId;

    @Column
    private String gid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        IndicatorTractId that = (IndicatorTractId) o;
        return Objects.equals(indicatorId, that.indicatorId) && Objects.equals(gid, that.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indicatorId, gid);
    }

}