package info.cinow.dto;

import java.util.Set;

import info.cinow.model.Indicator;
import info.cinow.model.MatchingTractSimilarIndicator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MatchedCensusTractDto
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MatchedCensusTractDto {

    private String id;

    private Set<MatchingTractSimilarIndicator> similarIndicators;

    private Set<Indicator> dissimilarIndicators;

    /**
     * the rank of the match for display purposes
     */
    private int rank;

    private double lifeExpectancyDifference;

}