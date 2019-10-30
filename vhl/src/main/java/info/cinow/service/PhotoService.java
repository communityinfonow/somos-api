package info.cinow.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.drew.lang.GeoLocation;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

import info.cinow.dto.PhotoDto;
import info.cinow.model.Location;
import info.cinow.model.Photo;

/**
 * PhotoService
 */
public interface PhotoService {

    public List<PhotoDto> uploadPhotos(MultipartFile[] photos) throws IOException;

    public Location getGpsCoordinates(Long id);
}