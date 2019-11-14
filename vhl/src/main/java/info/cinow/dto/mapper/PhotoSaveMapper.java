package info.cinow.dto.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import info.cinow.dto.PhotoSaveDto;
import info.cinow.model.Photo;

/**
 * PhotoSaveMapper
 */
@Component("photoSaveMapper")
public class PhotoSaveMapper implements PhotoMapper<PhotoSaveDto> {

    @Override
    public Optional<PhotoSaveDto> toDto(Photo photo) {
        Optional<PhotoSaveDto> dto;
        if (photo == null) {
            dto = Optional.empty();
        } else {
            PhotoSaveDto dtoObj = new PhotoSaveDto();
            dtoObj.setId(photo.getId());
            dtoObj.setDescription(photo.getDescription());
            dtoObj.setOwnerEmail(photo.getOwnerEmail());
            dtoObj.setOwnerFirstName(photo.getOwnerFirstName());
            dtoObj.setOwnerLastName(photo.getOwnerLastName());
            dtoObj.setApproved(photo.getApproved());
            dtoObj.setFileName(photo.getFileName());
            dto = Optional.of(dtoObj);
        }
        return dto;
    }

    @Override
    public Optional<Photo> toPhoto(PhotoSaveDto dto) {
        Optional<Photo> photo;
        if (dto == null) {
            photo = Optional.empty();
        } else {
            Photo photoObj = new Photo();
            photoObj.setId(dto.getId());
            photoObj.setDescription(dto.getDescription());
            photoObj.setOwnerEmail(dto.getOwnerEmail());
            photoObj.setOwnerFirstName(dto.getOwnerFirstName());
            photoObj.setOwnerLastName(dto.getOwnerLastName());
            photoObj.setApproved(dto.getApproved());
            photoObj.setFileName(dto.getFileName());
            photo = Optional.of(photoObj);
        }
        return photo;
    }
}