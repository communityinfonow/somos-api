package info.cinow.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.AdminPhotoLinksImpl;
import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.AdminPhotoSaveDto;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.service.CensusTractPhotoService;
import info.cinow.service.CensusTractService;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * CensusTractPhotoController
 */
@Slf4j
@RestController
@RequestMapping("/admin/census-tracts/{censusTractId}/photos")

public class AdminCensusTractPhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    @Autowired
    PhotoMapper<PhotoSaveDto> photoSaveMapper;

    @Autowired
    PhotoMapper<AdminPhotoSaveDto> adminPhotoSaveMapper;

    @Autowired
    CensusTractService censusTractService;

    @Autowired
    CensusTractPhotoService censusTractPhotoService;

    @Autowired
    private CensusTractPhotoLinks censusTractPhotoLinks;

    @Autowired
    private AdminPhotoLinksImpl adminPhotoLinks;

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhotoByIdForTract(@PathVariable("censusTractId") final String censusTractId,
            @PathVariable("id") final Long id) {
        return new EntityModel<>(
                this.photoMapper.toDto(this.censusTractPhotoService.getPublicPhotoByIdForTract(censusTractId, id))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                this.adminPhotoLinks.photo(censusTractId, id, true));
    }

    @PostMapping("/{id}")
    public EntityModel<PhotoDto> cropPhotoForTract(@PathVariable("censusTractId") final String censusTractId,
            @PathVariable("id") final Long id, @RequestParam("photo") final MultipartFile photo)
            throws ImageNameTooLongException, WrongFileTypeException {
        Photo savedPhoto = null;
        try {
            savedPhoto = photoService.cropPhoto(photo, id);
        } catch (final IOException e) {
            log.error("An error occurred saving the file for census tract: " + censusTractId + ", photo id: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred saving the file");
        } catch (final ImageTooLargeException e) {
            log.error("An error occurred saving the file for census tract: " + censusTractId + ", photo id: " + id, e);
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
        }

        final EntityModel<PhotoDto> dto = new EntityModel<>(
                this.photoMapper.toDto(savedPhoto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                this.censusTractPhotoLinks.photo(censusTractId, savedPhoto.getId(), true),
                this.censusTractPhotoLinks.photoFile(censusTractId, savedPhoto.getFilePathName(), false),
                this.censusTractPhotoLinks.croppedPhotoFile(censusTractId, savedPhoto.getCroppedFilePathName(), false));
        return dto;
    }

    @PutMapping("/{id}")
    public EntityModel<PhotoDto> updatePhotoInformation(@PathVariable("censusTractId") final String censusTractId,
            @PathVariable("id") final Long id, @RequestBody final AdminPhotoSaveDto photoDto) {
        final Photo photo = adminPhotoSaveMapper.toPhoto(photoDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        final CensusTract tract = new CensusTract();
        tract.setGid(censusTractId);
        photo.setCensusTract(tract);
        try {
            return new EntityModel<>(
                    photoMapper.toDto(photoService.updatePhoto(photo))
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                    this.censusTractPhotoLinks.photo(censusTractId, id, true));

        } catch (NoDescriptionException | CensusTractDoesNotExistException e) {
            log.error("An error occurred updating the photo information for tract: " + censusTractId + ", photo id:"
                    + id + ", photo: " + photoDto.toString(), e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public void deletePhotoByIdForTract(@PathVariable("censusTractId") final String censusTractId,
            @PathVariable("id") final Long photoId) {

        try {
            this.photoService.deletePhoto(photoId);
        } catch (final IOException e) {
            log.error("An error occurred deleting the photo for tract: " + censusTractId + ", photo: " + photoId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred deleting the file");
        }

    }

}