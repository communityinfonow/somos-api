package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.MapBreakDto;
import info.cinow.model.MapBreak;

/**
 * MapBreakMapperImpl
 */
@Component
public class MapBreakMapperImpl implements MapBreakMapper {

    @Override
    public MapBreakDto toDto(MapBreak mapBreak) {

        if (mapBreak.equals(null)) {
            return null;
        }
        MapBreakDto mapBreakDto = new MapBreakDto();
        mapBreakDto.setBreakValue(mapBreak.getBreakValue());
        mapBreakDto.setColor(mapBreak.getColor());
        return mapBreakDto;
    }

}