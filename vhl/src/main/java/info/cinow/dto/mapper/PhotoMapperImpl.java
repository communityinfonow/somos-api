package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.model.Photo;

/**
 * PhotoMapperImpl
 */
@Component("photoMapper")
public class PhotoMapperImpl implements PhotoMapper {

    @Override
    public PhotoDto toDto(Photo photo) {
        if (photo != null) {
            return new PhotoDto(photo.getId(), photo.getDescription());
        }
        return null;
    }

    @Override
    public Photo toPhoto(PhotoSaveDto dto, Integer tractId) {
        Photo photo = new Photo();
        photo.setTractId(tractId);
        photo.setId(dto.getId());
        photo.setOwnerEmail(dto.getOwnerEmail());
        photo.setOwnerFirstName(dto.getOwnerFirstName());
        photo.setOwnerLastName(dto.getOwnerLastName());
        return photo;
    }

}