package info.cinow.dto.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import info.cinow.dto.PhotoDto;
import info.cinow.model.Photo;

/**
 * PhotoMapperImpl
 */
@Component("photoMapper")
public class PhotoMapperImpl implements PhotoMapper<PhotoDto> {

    @Override
    public Optional<PhotoDto> toDto(Photo photo) {
        return photo == null ? Optional.empty()
                : Optional.of(new PhotoDto(photo.getId(), photo.getDescription(), null,
                        photo.getCensusTract() != null ? photo.getCensusTract().getGid() : null, photo.getFileName(),
                        photo.getApproved()));
    }

    @Override
    public Optional<Photo> toPhoto(PhotoDto dto) {
        Optional<Photo> photo;
        if (dto == null) {
            photo = Optional.empty();
        } else {
            Photo photoObj = new Photo();
            photoObj.setId(dto.getId());
            photoObj.setDescription(dto.getDescription());
            photoObj.setFileName(dto.getFileName());
            photoObj.setApproved(dto.getApproved());
            photo = Optional.of(photoObj);
        }
        return photo;
    }

}