package info.cinow.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import info.cinow.dto.PhotoDto;
import info.cinow.model.Location;
import info.cinow.model.PhotoOwner;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoController
 */
@Slf4j
@RestController
@RequestMapping("/photos")
@CrossOrigin(origins = "http://localhost:8080")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @GetMapping
    public String getPhotos() {
        return null;
        // TODO
    }

    @PostMapping
    public List<EntityModel<PhotoDto>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        // TODO: handler for file size too large
        // TODO: handler/response for file not saved correctly
        List<EntityModel<PhotoDto>> photoEntities = null;
        try {
            photoEntities = photoService.uploadPhotos(photos).stream().map(photo -> new EntityModel<>(photo,
                    linkTo(methodOn(PhotoController.class).getPhoto(photo.getId())).withSelfRel(),
                    linkTo(methodOn(PhotoController.class).getPhotoMetadata(photo.getId())).withRel("gps-coordinates")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("An error occurred", e);
        }

        return photoEntities;
    }

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhoto(@PathVariable("id") Long id) {
        return null;
        // TODO
    }

    @GetMapping("/{id}/gps-coordinates")
    public EntityModel<Location> getPhotoMetadata(@PathVariable("id") Long id) {
        EntityModel<Location> response = null;
        try {
            response = new EntityModel<>(this.photoService.getGpsCoordinates(id),
                    linkTo(methodOn(PhotoController.class).getPhoto(id)).withRel("photo"));
            // TODO: link to saving several photos or getting several photos
            // TODO: figure out creating reusable hateoas links
        } catch (Exception e) {
            log.error("an error occurred finding gps coordinates for id: " + id, e);
        }
        return response;
    }

    @PutMapping("/{id}")
    public String updatePhoto(@PathVariable("id") Long id, @RequestParam("photoOwner") PhotoOwner photoOwner) {
        return null;
        // TODO: if setting to approved, set publicity of file on S3 to public. Use the
        // urls from S3 for serving static images rather than loading them here and
        // returning them
    }

    // PutMapping("/{id}")
    // public String updatePhoto(@PathVariable("id") Long id,
    // @RequestParam("photoOwner") PhotoOwner photoOwner) {
    // return null;
    // // TODO: if setting to approved, set publicity of file on S3 to public. Use
    // the
    // // urls from S3 for serving static images rather than loading them here and
    // // returning them
    // }

    // TODO: DELETE

}