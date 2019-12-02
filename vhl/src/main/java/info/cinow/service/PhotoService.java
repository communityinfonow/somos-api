package info.cinow.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import info.cinow.dto.PhotoDto;
import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.model.Location;
import info.cinow.model.Photo;

/**
 * PhotoService
 */
public interface PhotoService {

    public List<Photo> uploadPhotos(MultipartFile[] photos) throws IOException;

    public Optional<Location> getGpsCoordinates(Long id);

    public Photo updatePhoto(Photo photo) throws NoDescriptionException, CensusTractDoesNotExistException;

    public List<Photo> getPhotos();

    public Photo cropPhoto(MultipartFile photo, Long photoId);

    public byte[] getPhoto(String fileName) throws IOException;

    public Optional<Photo> getPhoto(Long id);

    public void deletePhoto(Long id) throws IOException;
}