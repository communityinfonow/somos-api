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
            dtoObj.setDescription(photo.getDescription().orElse(null));
            dtoObj.setOwnerEmail(photo.getOwnerEmail().orElse(null));
            dtoObj.setOwnerFirstName(photo.getOwnerFirstName().orElse(null));
            dtoObj.setOwnerLastName(photo.getOwnerLastName().orElse(null));
            dtoObj.setApproved(photo.getApproved().orElse(false));
            dtoObj.setFileName(photo.getFileName().orElse(null));
            if (photo.getAudit() != null) {
                dtoObj.setLastEdited(photo.getAudit().getLastModified().toString());
                dtoObj.setLastEditedBy(photo.getAudit().getLastModifiedBy().toString());
            }

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