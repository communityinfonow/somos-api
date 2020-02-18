package info.cinow.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

import info.cinow.controller.connected_links.PhotoLinks;
import info.cinow.dto.PhotoAdminDto;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.Location;
import info.cinow.service.PhotoService;
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

    @Autowired
    private PhotoLinks photoLinks;

    @PostMapping
    public EntityModel<PhotoDto> savePhoto(@RequestParam("photo") MultipartFile photo) {

        PhotoDto dto;
        try {
            dto = this.photoMapper.toDto(photoService.uploadPhoto(photo)).orElseThrow(NoSuchElementException::new);
        } catch (IOException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred saving the file");
        } catch (ImageTooLargeException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
        } catch (ImageNameTooLongException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (WrongFileTypeException e) {
            log.error("An error occurred saving the file", e);
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
        }
        return new EntityModel<>(dto, this.photoLinks.photoMetadata(dto.getId(), false));
        // TODO if the lat/lng doesn't exist within the bounds of the geographies, don't
        // return lat/lng
    }

    @GetMapping("/{id}/gps-coordinates")
    public EntityModel<Location> getPhotoMetadata(@PathVariable("id") Long id) {

        return new EntityModel<>(this.photoService.getGpsCoordinates(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lat/lng could not be extracted from the photo")));
    }

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhoto(@PathVariable("id") Long photoId) {
        PhotoDto dto = this.photoMapper
                .toDto(this.photoService.getPhotoById(photoId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The photo could not be found")))
                .orElse(null);
        return new EntityModel<>(dto);
    }

}
