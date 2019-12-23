package info.cinow.controller.admin;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.CensusTractPhotoLinks;
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
    CensusTractService censusTractService;

    @Autowired
    CensusTractPhotoService censusTractPhotoService;

    private final CensusTractPhotoLinks censusTractPhotoLinks;

    public AdminCensusTractPhotoController() {
        this.censusTractPhotoLinks = new CensusTractPhotoLinks();
    }

    @PutMapping("/{id}")
    public EntityModel<PhotoDto> updatePhotoForTract(@PathVariable("censusTractId") final Integer censusTractId,
            @PathVariable("id") final Long id, @RequestBody final PhotoSaveDto photoDto) {
        final Photo photo = photoSaveMapper.toPhoto(photoDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        final CensusTract tract = new CensusTract();
        tract.setGid(censusTractId);
        photo.setCensusTract(tract);
        try {
            return new EntityModel<>(photoMapper.toDto(photoService.updatePhoto(photo))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""))); // TODO
            // hateoas
            // links
        } catch (NoDescriptionException | CensusTractDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @PostMapping("/{id}")
    // TODO secure with auth
    public EntityModel<PhotoDto> cropPhotoForTract(@PathVariable("censusTractId") final Integer censusTractId,
            @PathVariable("id") final Long id, @RequestParam("photo") final MultipartFile photo)
            throws ImageNameTooLongException, WrongFileTypeException {
        Photo savedPhoto = null;
        try {
            savedPhoto = photoService.cropPhoto(photo, id);
        } catch (final IOException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred saving the file");
        } catch (final ImageTooLargeException e) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
        }

        final EntityModel<PhotoDto> dto = new EntityModel<>(
                this.photoMapper.toDto(savedPhoto).orElseThrow(NoSuchElementException::new),
                this.censusTractPhotoLinks.photo(censusTractId, savedPhoto.getId(), true),
                this.censusTractPhotoLinks.photoFile(censusTractId, savedPhoto.getFilePathName(), false),
                this.censusTractPhotoLinks.croppedPhotoFile(censusTractId, savedPhoto.getCroppedFilePathName(), false));
        return dto;
    }

    @DeleteMapping("/{id}")
    public void deletePhotoByIdForTract(@PathVariable("censusTractId") final Integer censusTractId,
            @PathVariable("id") final Long photoId) {

        try {
            this.photoService.deletePhoto(photoId);
        } catch (final IOException e) {
            log.error("An error occurred deleting the photo for tract: " + censusTractId + ", photo: " + photoId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred deleting the file");
        }

    }

}