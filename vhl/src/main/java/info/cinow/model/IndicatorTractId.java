package info.cinow.model;

import java.io.Serializable;

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
    private Integer gid;

}