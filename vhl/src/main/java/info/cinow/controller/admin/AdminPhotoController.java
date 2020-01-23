package info.cinow.controller.admin;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.controller.connected_links.AdminPhotoLinks;
import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.PhotoAdminDto;
import info.cinow.dto.PhotoDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * PhotoController
 */
@Slf4j
@RestController()
@RequestMapping("/admin/photos")
public class AdminPhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper<PhotoDto> photoMapper;

    @Autowired
    PhotoMapper<PhotoAdminDto> photoAdminMapper;

    private AdminPhotoLinks photoLinks;

    private CensusTractLinks censusTractLinks;

    private CensusTractPhotoLinks censusTractPhotoLinks;

    public AdminPhotoController() {
        this.photoLinks = new AdminPhotoLinks();
        this.censusTractLinks = new CensusTractLinks();
        this.censusTractPhotoLinks = new CensusTractPhotoLinks();

    }

    @GetMapping
    public CollectionModel<EntityModel<PhotoAdminDto>> getAllPhotos() {
        // TODO: do links and entitymodels and collection models correctly
        CollectionModel<EntityModel<PhotoAdminDto>> photoEntities = new CollectionModel<>(Arrays.asList());

        photoEntities = new CollectionModel<>(photoService.getAllPhotos().stream().map(photo -> {
            PhotoAdminDto photoDto = photoAdminMapper.toDto(photo).orElseThrow(NoSuchElementException::new);

            return new EntityModel<>(photoDto, this.photoLinks.photos(false),
                    this.censusTractPhotoLinks.photoFile(photoDto.getCensusTractId(), photo.getFilePathName(), false),
                    this.censusTractPhotoLinks.croppedPhotoFile(photoDto.getCensusTractId(),
                            photo.getCroppedFilePathName(), false),
                    this.photoLinks.photo(photoDto.getCensusTractId(), photoDto.getId(), true),
                    this.photoLinks.photoMetadata(photoDto.getId(), false), this.photoLinks.photos(false));
        }).collect(Collectors.toList()));

        return photoEntities;
    }

}