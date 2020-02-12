package info.cinow.dto;

import java.util.Set;

import lombok.Data;

/**
 * IndicatorDataSetDto
 */
@Data
public class IndicatorDataSetDto {

    private Set<IndicatorDataDto> indicatorData;

    private double maxValue;

    private double minValue;

}