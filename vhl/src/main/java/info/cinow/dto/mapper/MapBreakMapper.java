package info.cinow.dto.mapper;

import info.cinow.dto.MapBreakDto;
import info.cinow.model.MapBreak;

/**
 * MapBreaksMapper
 */
public interface MapBreakMapper {

    public MapBreakDto toDto(MapBreak mapBreak);

}