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

    private Long order;

    private String notation;

    private Boolean isLifeExpectancy;

    private ValueType valueType;

}