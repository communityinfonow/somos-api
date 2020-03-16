package info.cinow.dto;

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

}