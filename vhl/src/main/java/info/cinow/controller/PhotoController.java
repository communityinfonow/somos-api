package info.cinow.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.Location;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoController
 */
@Slf4j
@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    // TODO: secure behind auth
    @GetMapping
    public CollectionModel<EntityModel<PhotoDto>> getPhotos() {
        // TODO: do links and entitymodels and collection models correctly
        CollectionModel<EntityModel<PhotoDto>> photoEntities = new CollectionModel<>(Arrays.asList());
        try {
            photoEntities = new CollectionModel<>(photoService.getPhotos().stream().map(photo -> {
                PhotoDto photoDto = photoMapper.toDto(photo).orElseThrow(NoSuchElementException::new); // TODO really
                                                                                                       // think out this
                                                                                                       // null handling
                return new EntityModel<>(photoDto, this.photosLink(false),
                        this.photoFileLink(photoDto.getCensusTractId(), photo.getFilePathName(), false),
                        this.photoLink(photoDto.getCensusTractId(), photoDto.getId(), true),
                        this.photoMetadataLink(photoDto.getId(), false), this.photosLink(false));
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("An error occurred", e);
            // TODO response error handler
        }
        return photoEntities;
    }

    @PostMapping
    public CollectionModel<EntityModel<PhotoDto>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        // TODO: saving of file name is not working
        CollectionModel<EntityModel<PhotoDto>> photoEntities = new CollectionModel<>(Arrays.asList());
        try {
            photoEntities = new CollectionModel<>(photoService.uploadPhotos(photos).stream().map(photo -> {
                PhotoDto photoDto = this.photoMapper.toDto(photo).orElseThrow(NoSuchElementException::new);
                return new EntityModel<>(photoDto, this.photoMetadataLink(photoDto.getId(), false),
                        this.photosLink(false));
            }).collect(Collectors.toList()), this.photosLink(false));
        } catch (IOException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occureed saving the file(s)");
        }

        return photoEntities;
    }

    @GetMapping("/{id}/gps-coordinates")
    public EntityModel<Location> getPhotoMetadata(@PathVariable("id") Long id) {

        return new EntityModel<>(this.photoService.getGpsCoordinates(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), this.photosLink(false));
    }

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhoto(@PathVariable("id") Long photoId) {
        PhotoDto dto = this.photoMapper
                .toDto(this.photoService.getPhoto(photoId).orElseThrow(EntityNotFoundException::new))
                .orElseThrow(EntityNotFoundException::new);
        return new EntityModel<>(dto);
    }

    protected Link photoMetadataLink(Long id, Boolean self) {
        return configureRelation(linkTo(methodOn(PhotoController.class).getPhotoMetadata(id)), self, "gps-coordinates");
    }

    protected Link photoFileLink(Integer tractId, String fileName, Boolean self) {
        return configureRelation(linkTo(ReflectionUtils.findMethod(CensusTractPhotoController.class, "getPhotoFile",
                Integer.class, String.class), tractId, fileName), self, "photo-file");
    }

    protected Link photoLink(Integer tractId, Long photoId, Boolean self) {
        return configureRelation(linkTo(methodOn(CensusTractPhotoController.class).getPhoto(tractId, photoId)), self,
                "photo");
    }

    protected Link photosLink(Boolean self) {
        return configureRelation(linkTo(methodOn(PhotoController.class).getPhotos()), self, "photos");
    }

    private Link configureRelation(WebMvcLinkBuilder linkBuilder, Boolean self, String defaultRelationName) {
        return self ? linkBuilder.withSelfRel() : linkBuilder.withRel(defaultRelationName);
    }

}
