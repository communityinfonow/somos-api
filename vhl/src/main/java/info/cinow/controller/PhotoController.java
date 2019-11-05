package info.cinow.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.dto.PhotoDto;
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

    // TODO: secure behind auth
    @GetMapping
    public List<EntityModel<PhotoDto>> getPhotos() {
        List<EntityModel<PhotoDto>> photoEntities = null;
        try {
            photoEntities = photoService.getPhotos().stream()
                    .map(photo -> new EntityModel<>(photo,
                            linkTo(methodOn(PhotoController.class).getPhotos()).withSelfRel(),
                            linkTo(methodOn(PhotoController.class).getPhoto(photo.getId())).withRel("photo"),
                            this.photoMetadataLink(photo.getId())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("An error occurred", e);
        }

        if (photoEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photos do not exist");
        }
        return photoEntities;
    }

    @PostMapping
    public List<EntityModel<PhotoDto>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        List<EntityModel<PhotoDto>> photoEntities = null;
        try {
            photoEntities = photoService.uploadPhotos(photos).stream()
                    .map(photo -> new EntityModel<>(photo, this.photoMetadataLink(photo.getId()), this.photosLink()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occureed saving the file(s)");
        }

        return photoEntities;
    }

    // TODO: secure behind auth
    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhoto(@PathVariable("id") Long id) {
        return null;
        // TODO fill in
    }

    @GetMapping("/{id}/gps-coordinates")
    public EntityModel<Location> getPhotoMetadata(@PathVariable("id") Long id) {

        return new EntityModel<>(
                this.photoService.getGpsCoordinates(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                this.photoLink(id), this.photosLink());
    }

    private Link photoMetadataLink(Long id) {
        return linkTo(methodOn(PhotoController.class).getPhotoMetadata(id)).withRel("gps-coordinates");
    }

    private Link photoLink(Long id) {
        return linkTo(methodOn(PhotoController.class).getPhoto(id)).withRel("photo");
    }

    private Link photosLink() {
        return linkTo(methodOn(PhotoController.class).getPhotos()).withRel("photos");
    }
}