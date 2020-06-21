package info.cinow.dto.mapper;

import info.cinow.dto.IndicatorDto;
import info.cinow.model.Indicator;

/**
 * IndicatorMapper
 */
public interface IndicatorMapper {

    public IndicatorDto toDto(Indicator indicator);
}