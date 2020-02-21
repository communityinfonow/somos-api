package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.IndicatorDataDto;
import info.cinow.model.IndicatorData;

/**
 * IndicatorDataMapperImpl
 */
@Component
public class IndicatorDataMapperImpl implements IndicatorDataMapper {

    @Override
    public IndicatorDataDto toDto(IndicatorData data) {
        if (data.equals(null)) {
            return null;
        }

        IndicatorDataDto dto = new IndicatorDataDto();
        dto.setMarginOfError(data.getMarginOfError());
        dto.setMaxValue(data.getMaxValue());
        dto.setValue(data.getValue());
        dto.setCensusTractId(data.getCensusTract().getGid());

        return dto;
    }

}