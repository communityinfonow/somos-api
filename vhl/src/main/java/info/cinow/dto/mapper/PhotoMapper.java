package info.cinow.dto.mapper;

import info.cinow.dto.PhotoDto;
import info.cinow.model.Photo;

/**
 * PhotoMapper
 */
public class PhotoMapper {

    public static PhotoDto toPhotoDto(Photo photo) {
        return new PhotoDto(photo.getId(), photo.getDescription(), photo.getTractId());

    }
}