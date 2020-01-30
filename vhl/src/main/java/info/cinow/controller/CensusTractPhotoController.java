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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.NoDescriptionException;
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
                return new CollectionModel<>(this.censusTractPhotoService.getAllPublicPhotosForTract(censusTractId)
                                .stream().map(photo -> {
                                        return new EntityModel<>(
                                                        this.photoMapper.toDto(photo)
                                                                        .orElseThrow(() -> new ResponseStatusException(
                                                                                        HttpStatus.NOT_FOUND)),
                                                        this.censusTractPhotoLinks.photo(censusTractId, photo.getId(),
                                                                        true),
                                                        this.censusTractPhotoLinks.photoFile(censusTractId,
                                                                        photo.getFilePathName(), false),
                                                        this.censusTractPhotoLinks.croppedPhotoFile(censusTractId,
                                                                        photo.getCroppedFilePathName(), false));
                                }).collect(Collectors.toList())

                );
        }

        @GetMapping("/{id}")
        public EntityModel<PhotoDto> getPhotoByIdForTract(@PathVariable("censusTractId") final Integer censusTractId,
                        @PathVariable("id") final Long id) {
                return new EntityModel<>(this.photoMapper
                                .toDto(this.censusTractPhotoService.getPublicPhotoByIdForTract(censusTractId, id))
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                                this.censusTractPhotoLinks.photo(censusTractId, id, true));
        }

        @PutMapping("/{id}")
        public EntityModel<PhotoDto> updatePhotoInformationForTract(
                        @PathVariable("censusTractId") final Integer censusTractId, @PathVariable("id") final Long id,
                        @RequestBody final PhotoSaveDto photoDto) {
                final Photo photo = photoSaveMapper.toPhoto(photoDto)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
                final CensusTract tract = new CensusTract();
                tract.setGid(censusTractId);
                photo.setCensusTract(tract);
                try {
                        return new EntityModel<>(
                                        photoMapper.toDto(photoService.updatePhoto(photo)).orElseThrow(
                                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                                        this.censusTractPhotoLinks.photo(censusTractId, id, true)); // TODO

                } catch (NoDescriptionException | CensusTractDoesNotExistException e) {
                        log.error("An error occurred updating the photo information for tract: " + censusTractId
                                        + ", photo id:" + id + ", photo: " + photoDto.toString(), e);
                        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
                }
        }

        // TODO: secure for public use by modifying query to get accepted
        @GetMapping(value = "/file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        public byte[] getPhotoFileByNameForTract(@PathVariable("censusTractId") final Integer censusTractId,
                        @PathVariable("fileName") final String fileName) {
                try {
                        return photoService.getPublicPhotoByFileName(fileName);
                } catch (final IOException e) {
                        log.error("An error occurred loading the photo: " + fileName, e);
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                        "An error occurred loading the file");
                }
        }

}