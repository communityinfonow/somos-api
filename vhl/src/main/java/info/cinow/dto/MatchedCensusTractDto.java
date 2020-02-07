package info.cinow.dto;

import java.util.List;

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

    private List<MatchingTractSimilarIndicator> similarIndicators;

    /**
     * the rank of the match for display purposes
     */
    private int rank;

    private double lifeExpectancyDifference;

}