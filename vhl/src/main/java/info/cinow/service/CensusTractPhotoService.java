package info.cinow.service;

import java.util.List;

import info.cinow.model.Photo;

/**
 * CensusTractPhotoService
 */
public interface CensusTractPhotoService {

    public List<Photo> getAllPhotosForTract(Integer tractId);

    public Photo getPhotoByIdForTract(Integer tractId, Long photoId);
}