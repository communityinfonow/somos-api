package info.cinow.dto.mapper;

import java.util.List;

import info.cinow.dto.LocationSuggestionDto;

/**
 * LocationSuggestionMapper1
 */
public interface LocationSuggestionMapper<T> {

    public List<LocationSuggestionDto> toDto(T model);

}