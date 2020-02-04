package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * CensusTractData
 */
@Entity
@Table(name = "indicator_data")
@Data
public class IndicatorData {

    @EmbeddedId
    private IndicatorTractId id;

    @Column(name = "is_life_expectancy")
    private Boolean isLifeExpectancy;

    @Column
    private Double value;

    /**
     * MOE is margin of error. This is the low value when applying margin of error.
     */
    @Column(name = "moe_value_low")
    private Double moeValueLow;

    /**
     * MOE is margin of error. This is the high value when applying margin of error.
     */
    @Column(name = "moe_value_high")
    private Double moeValueHigh;

}