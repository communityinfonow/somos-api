package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.IndicatorDto;
import info.cinow.model.Indicator;

/**
 * IndicatorMapperImpl
 */
@Component
public class IndicatorMapperImpl implements IndicatorMapper {

    @Override
    public IndicatorDto toDto(Indicator indicator) {
        if (indicator.equals(null)) {
            return null;
        }

        IndicatorDto dto = new IndicatorDto();
        dto.setDataLabel(indicator.getDataLabel());
        dto.setDescription(indicator.getDescription());
        dto.setId(indicator.getId());
        dto.setIsLifeExpectancy(indicator.getIsLifeExpectancy());
        dto.setName(indicator.getName());
        dto.setValueType(indicator.getValueType());

        return dto;
    }

}