package info.cinow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.PhotoDto;
import info.cinow.dto.PhotoSaveDto;
import info.cinow.dto.mapper.PhotoMapper;
import info.cinow.model.Photo;
import info.cinow.service.PhotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * CensusTractPhotoController
 */
@Slf4j
@RestController
@RequestMapping("/census-tracts/{tractId}/photos")
@CrossOrigin(origins = "*")
// TODO: move to census tract controller?
public class CensusTractPhotoController {

    @Autowired
    PhotoService photoService;

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
            @RequestBody PhotoSaveDto photo) {
        PhotoDto dto = photoService.updatePhoto(PhotoMapper.toPhoto(photo, tractId, id));
        EntityModel<PhotoDto> response = new EntityModel<>(dto);

        return response;
        // TODO: split into separate REST calls to create a photo owner and to update
        // photo with tract Id
    }

}