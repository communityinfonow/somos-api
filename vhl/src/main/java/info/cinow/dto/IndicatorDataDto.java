package info.cinow.dto;

import info.cinow.model.MarginOfError;
import lombok.Data;

/**
 * IndicatorDataDto
 */
@Data
public class IndicatorDataDto {

    private Double value;

    private Double maxValue;

    private MarginOfError marginOfError;

    private String censusTractId;
}