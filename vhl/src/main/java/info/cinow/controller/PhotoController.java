package info.cinow.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.controller.connected_links.PhotoLinks;
import info.cinow.dto.PhotoAdminDto;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.Location;
import info.cinow.service.PhotoService;
import info.cinow.utility.FileSaveErrorHandling;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoController
 */
@Slf4j
@RestController()
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    @Autowired
    PhotoMapper<PhotoAdminDto> photoAdminMapper;

    private PhotoLinks photoLinks;

    private CensusTractLinks censusTractLinks;

    private CensusTractPhotoLinks censusTractPhotoLinks;

    private FileSaveErrorHandling fileErrorHandling;

    public PhotoController() {
        this.photoLinks = new PhotoLinks();
        this.censusTractLinks = new CensusTractLinks();
        this.censusTractPhotoLinks = new CensusTractPhotoLinks();
        this.fileErrorHandling = new FileSaveErrorHandling();
    }

    // TODO: secure behind auth
    // TODO refactor PhotoSaveDto since we're now using it to get as well
    @GetMapping
    public CollectionModel<EntityModel<PhotoAdminDto>> getPhotos() {
        // TODO: do links and entitymodels and collection models correctly
        CollectionModel<EntityModel<PhotoAdminDto>> photoEntities = new CollectionModel<>(Arrays.asList());
        try {
            photoEntities = new CollectionModel<>(photoService.getPhotos().stream().map(photo -> {
                PhotoAdminDto photoDto = photoAdminMapper.toDto(photo).orElseThrow(NoSuchElementException::new); // TODO
                // really
                // think out this
                // null handling
                return new EntityModel<>(photoDto, this.photoLinks.photos(false),
                        this.censusTractPhotoLinks.photoFile(photoDto.getCensusTractId(), photo.getFilePathName(),
                                false),
                        this.censusTractPhotoLinks.croppedPhotoFile(photoDto.getCensusTractId(),
                                photo.getCroppedFilePathName(), false),
                        this.censusTractPhotoLinks.photo(photoDto.getCensusTractId(), photoDto.getId(), true),
                        this.photoLinks.photoMetadata(photoDto.getId(), false), this.photoLinks.photos(false));
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("An error occurred", e);
            // TODO response error handler
        }
        return photoEntities;
    }

    @PostMapping
    public EntityModel<PhotoDto> savePhoto(@RequestParam("photo") MultipartFile photo) {
        this.fileErrorHandling.checkContentType(photo);
        this.fileErrorHandling.checkFileSize(photo);
        // TODO test error throwing

        // TODO: saving of file name is not working. Look into this still?
        PhotoDto dto;
        try {
            dto = this.photoMapper.toDto(photoService.uploadPhoto(photo)).orElseThrow(NoSuchElementException::new);
        } catch (IOException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred saving the file");
        }
        return new EntityModel<>(dto, this.photoLinks.photoMetadata(dto.getId(), false), this.photoLinks.photos(false));
    }

    @GetMapping("/{id}/gps-coordinates")
    public EntityModel<Location> getPhotoMetadata(@PathVariable("id") Long id) {

        return new EntityModel<>(this.photoService.getGpsCoordinates(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), this.photoLinks.photos(false));
    }

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhoto(@PathVariable("id") Long photoId) {
        PhotoDto dto = this.photoMapper
                .toDto(this.photoService.getPhoto(photoId).orElseThrow(EntityNotFoundException::new))
                .orElseThrow(EntityNotFoundException::new);
        return new EntityModel<>(dto);
    }

}
