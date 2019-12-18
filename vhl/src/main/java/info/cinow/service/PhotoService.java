package info.cinow.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import info.cinow.exceptions.CensusTractDoesNotExistException;
import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.NoDescriptionException;
import info.cinow.exceptions.WrongFileTypeException;
import info.cinow.model.Location;
import info.cinow.model.Photo;

/**
 * PhotoService
 */
public interface PhotoService {

    public Photo uploadPhoto(MultipartFile photo)
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException;

    public Optional<Location> getGpsCoordinates(Long id);

    public Photo updatePhoto(Photo photo) throws NoDescriptionException, CensusTractDoesNotExistException;

    public List<Photo> getAllPhotos();

    public Photo cropPhoto(MultipartFile photo, Long photoId)
            throws IOException, ImageTooLargeException, ImageNameTooLongException, WrongFileTypeException;

    public byte[] getPhotoByFileName(String fileName) throws IOException;

    public Optional<Photo> getPhotoById(Long id);

    public void deletePhoto(Long id) throws IOException;
}