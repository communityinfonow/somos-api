package info.cinow.dto;

import java.util.Set;

import info.cinow.model.IndicatorData;
import lombok.Data;

/**
 * IndicatorDataSetDto
 */
@Data
public class IndicatorDataSet {

    private Set<IndicatorData> indicatorData;

    private double maxValue;

    private double minValue;

}