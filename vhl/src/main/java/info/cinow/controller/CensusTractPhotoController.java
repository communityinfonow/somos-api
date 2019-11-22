package info.cinow.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.CensusTract;
import info.cinow.model.Photo;
import info.cinow.service.CensusTractService;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * CensusTractPhotoController
 */
@Slf4j
@RestController
@RequestMapping("/census-tracts/{tractId}/photos")

public class CensusTractPhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    @Autowired
    PhotoMapper<PhotoSaveDto> photoSaveMapper;

    @Autowired
    CensusTractService censusTractService;

    private CensusTractPhotoLinks censusTractPhotoLinks;

    public CensusTractPhotoController() {
        this.censusTractPhotoLinks = new CensusTractPhotoLinks();
    }

    @GetMapping()
    public EntityModel<Photo> getPhotos(@PathVariable("tractId") Integer tractId) {
        return null;
    }

    @GetMapping("/{id}")
    public EntityModel<Photo> getPhoto(@PathVariable("tractId") Integer tractId, @PathVariable("id") Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public EntityModel<PhotoDto> updatePhoto(@PathVariable("tractId") Integer tractId, @PathVariable("id") Long id,
            @RequestBody PhotoSaveDto photoDto) {
        Photo photo = convertToEntity(photoDto);
        photo.setCensusTract(convertGidToCensusTract(tractId));
        return new EntityModel<>(
                photoMapper.toDto(photoService.updatePhoto(photo)).orElseThrow(NoSuchElementException::new)); // TODO
                                                                                                              // hateoas
                                                                                                              // links
    }

    @PostMapping("/{id}")
    public EntityModel<PhotoDto> cropPhoto(@PathVariable("tractId") Integer tractId, @PathVariable("id") Long id,
            @RequestParam("photo") MultipartFile photo) {
        Photo savedPhoto = photoService.cropPhoto(photo, id);
        EntityModel<PhotoDto> dto = new EntityModel<>(
                this.photoMapper.toDto(savedPhoto).orElseThrow(NoSuchElementException::new),
                this.censusTractPhotoLinks.photo(savedPhoto.getCensusTract().getGid(), savedPhoto.getId(), true),
                this.censusTractPhotoLinks.photoFile(tractId, savedPhoto.getFilePathName(), false),
                this.censusTractPhotoLinks.croppedPhotoFile(tractId, savedPhoto.getCroppedFilePathName(), false));
        return dto;
    }

    // TODO: secure for public use by modifying query to get accepted
    @GetMapping(value = "/file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getPhotoFile(@PathVariable("tractId") Integer tractId, @PathVariable("fileName") String fileName) {
        try {
            return photoService.getPhoto(fileName);
        } catch (IOException e) {
            log.error("An error occurred loading the photo: " + fileName, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred loading the file");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable("tractId") Integer tractId, @PathVariable("id") Long photoId) {
        try {
            this.photoService.deletePhoto(photoId);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("An error occurred deleting the photo", e);
        }
    }

    private CensusTract convertGidToCensusTract(Integer tractId) {
        if (tractId != null) {
            CensusTract censusTract = this.censusTractService.getCensusTract(tractId);
            return censusTract;
        }
        return null;
    }

    private Photo convertToEntity(PhotoSaveDto dto) {
        Photo photo = photoSaveMapper.toPhoto(dto).orElse(null); // TODO: maybe do something else other than null here?

        if (dto.getId() != null) {
            try {
                Photo oldPhoto = this.photoService.getPhoto(dto.getId()).get();
                photo.setCensusTract(oldPhoto.getCensusTract());
                photo.setImageRepositoryPath(oldPhoto.getImageRepositoryPath());
                photo.setOwnerEmail(oldPhoto.getOwnerEmail());
                photo.setOwnerFirstName(oldPhoto.getOwnerFirstName());
                photo.setOwnerLastName(oldPhoto.getOwnerLastName());
                photo.setLatitude(oldPhoto.getLatitude());
                photo.setLongitude(oldPhoto.getLongitude());

            } catch (NoSuchElementException e) {
                log.info("No photo exists for id: " + dto.getId(), e);
            }
        }
        return photo;
    }

}