package info.cinow.dto;

import info.cinow.model.ValueType;
import lombok.Data;

/**
 * IndicatorDto
 */
@Data
public class IndicatorDto {

    private Long id;

    private String name;

    private String description;

    private String dataLabel;

    private Boolean isLifeExpectancy;

    private ValueType valueType;

}