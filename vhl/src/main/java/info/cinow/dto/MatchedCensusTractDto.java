package info.cinow.dto;

import java.util.List;

import org.springframework.hateoas.EntityModel;

import info.cinow.model.Indicator;
import info.cinow.model.MatchingTractDissimilarIndicator;
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

    private List<EntityModel<MatchingTractSimilarIndicator>> similarIndicators;

    private List<EntityModel<MatchingTractDissimilarIndicator>> dissimilarIndicators;

    /**
     * the rank of the match for display purposes
     */
    private int rank;

    private double lifeExpectancyDifference;

    private EntityModel<Indicator> lifeExpectancyIndicator;

}