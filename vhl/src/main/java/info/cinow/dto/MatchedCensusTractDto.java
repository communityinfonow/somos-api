package info.cinow.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MatchedCensusTractDto
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MatchedCensusTractDto {

    private String id;

    /**
     * the rank of the match for display purposes
     */
    private int rank;

    private double lifeExpectancyDifference;

}