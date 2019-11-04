package info.cinow.dto.mapper;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.model.Photo;

/**
 * PhotoMapper
 */
public class PhotoMapper {

    public static PhotoDto toPhotoDto(Photo photo) {
        return new PhotoDto(photo.getId(), photo.getDescription(), photo.getTractId());

    }

    public static Photo toPhoto(PhotoSaveDto dto, Integer tractId, Long id) {
        Photo photo = new Photo();
        photo.setTractId(tractId);
        photo.setId(id);
        photo.setOwnerEmail(dto.getOwnerEmail());
        photo.setOwnerFirstName(dto.getOwnerFirstName());
        photo.setOwnerLastName(dto.getOwnerLastName());
        return photo;
    }
}