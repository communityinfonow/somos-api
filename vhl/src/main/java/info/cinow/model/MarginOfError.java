package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MarginOfError
 */
@Embeddable
public class MarginOfError {

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