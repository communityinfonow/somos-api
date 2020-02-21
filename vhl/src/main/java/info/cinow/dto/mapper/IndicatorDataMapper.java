package info.cinow.dto.mapper;

import info.cinow.dto.IndicatorDataDto;
import info.cinow.model.IndicatorData;

/**
 * IndicatorDataMapper
 */
public interface IndicatorDataMapper {
    public IndicatorDataDto toDto(IndicatorData data);

}