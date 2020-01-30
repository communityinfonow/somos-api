package info.cinow.dto.mapper;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;

import info.cinow.dto.AdminPhotoSaveDto;
import info.cinow.dto.AdminPhotoSaveDto;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;

/**
 * PhotoAdminMapper
 */
@Component("adminPhotoSaveMapper")
public class AdminPhotoSaveMapper implements PhotoMapper<AdminPhotoSaveDto> {

    @Override
    public Optional<AdminPhotoSaveDto> toDto(Photo photo) {

        if (photo == null) {
            return Optional.empty();
        }

        AdminPhotoSaveDto dto = new AdminPhotoSaveDto();
        dto.setApproved(photo.getApproved().orElse(false));
        dto.setDescription(photo.getDescription().orElse(null));
        dto.setFileName(photo.getFileName().orElse(null));
        dto.setId(photo.getId());
        dto.setOwnerEmail(photo.getOwnerEmail().orElse(null));
        if (photo.getAudit() != null) {
            if (photo.getAudit().getLastModifiedBy() != null) {
                dto.setLastEditedBy(photo.getAudit().getLastModifiedBy().toString());
            }
            if (photo.getAudit().getLastModified() != null) {
                dto.setLastEdited(photo.getAudit().getLastModified().format(DateTimeFormatter.ISO_LOCAL_DATE));
            }

        }
        dto.setOwnerFirstName(photo.getOwnerFirstName().orElse(null));
        dto.setOwnerLastName(photo.getOwnerLastName().orElse(null));
        return Optional.of(dto);
    }

    @Override
    public Optional<Photo> toPhoto(AdminPhotoSaveDto dto) {
        if (dto == null) {
            return Optional.empty();
        }

        Photo photo = new Photo();
        photo.setApproved(dto.getApproved());
        CensusTract tract = new CensusTract();
        photo.setCensusTract(tract);
        photo.setDescription(dto.getDescription());
        photo.setFileName(dto.getFileName());
        photo.setId(dto.getId());
        photo.setOwnerEmail(dto.getOwnerEmail());
        photo.setOwnerFirstName(dto.getOwnerFirstName());
        photo.setOwnerLastName(dto.getOwnerLastName());

        return Optional.of(photo);
    }

}