package info.cinow.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import info.cinow.dto.PhotoDto;
import info.cinow.model.Location;
import info.cinow.model.Photo;

/**
 * PhotoService
 */
public interface PhotoService {

    public List<PhotoDto> uploadPhotos(MultipartFile[] photos) throws IOException;

    public Optional<Location> getGpsCoordinates(Long id);

    public PhotoDto updatePhoto(Photo photo);

    public List<PhotoDto> getPhotos();

    public PhotoDto replacePhoto(MultipartFile photo, Long photoId);
}