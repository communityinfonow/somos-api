package info.cinow.dto.mapper;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.model.Photo;

/**
 * PhotoMapper
 */
public interface PhotoMapper {

    public PhotoDto toDto(Photo photo);

    public Photo toPhoto(PhotoSaveDto dto, Integer tractId);
}