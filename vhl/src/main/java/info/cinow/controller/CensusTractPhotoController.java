package info.cinow.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.service.CensusTractPhotoService;
import info.cinow.service.CensusTractService;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * CensusTractPhotoController
 */
@Slf4j
@RestController
@RequestMapping("/census-tracts/{censusTractId}/photos")

public class CensusTractPhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    CensusTractPhotoService censusTractPhotoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    @Autowired
    PhotoMapper<PhotoSaveDto> photoSaveMapper;

    @Autowired
    CensusTractService censusTractService;

    private final CensusTractPhotoLinks censusTractPhotoLinks;

    public CensusTractPhotoController() {
        this.censusTractPhotoLinks = new CensusTractPhotoLinks();
    }

    @GetMapping()
    public CollectionModel<EntityModel<PhotoDto>> getAllPhotosForTract(
            @PathVariable("censusTractId") final Integer censusTractId) {
        return new CollectionModel<>(
                this.censusTractPhotoService.getAllPhotosForTract(censusTractId).stream().map(photo -> {
                    return new EntityModel<>(this.photoMapper.toDto(photo).orElseThrow(NoSuchElementException::new),
                            this.censusTractPhotoLinks.photo(censusTractId, photo.getId(), true),
                            this.censusTractPhotoLinks.photoFile(censusTractId, photo.getFilePathName(), false),
                            this.censusTractPhotoLinks.croppedPhotoFile(censusTractId, photo.getCroppedFilePathName(),
                                    false));
                }).collect(Collectors.toList())

        );
    }

    @GetMapping("/{id}")
    public EntityModel<PhotoDto> getPhotoByIdForTract(@PathVariable("censusTractId") final Integer censusTractId,
            @PathVariable("id") final Long id) {
        return new EntityModel<>(
                this.photoMapper.toDto(this.censusTractPhotoService.getPhotoByIdForTract(censusTractId, id))
                        .orElseThrow(NoSuchElementException::new),
                this.censusTractPhotoLinks.photo(censusTractId, id, true));
    }

    // TODO: secure for public use by modifying query to get accepted
    @GetMapping(value = "/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getPhotoFileByNameForTract(@PathVariable("censusTractId") final Integer censusTractId,
            @PathVariable("fileName") final String fileName) {
        try {
            return photoService.getPhotoByFileName(fileName);
        } catch (final IOException e) {
            log.error("An error occurred loading the photo: " + fileName, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred loading the file");
        }
    }

}