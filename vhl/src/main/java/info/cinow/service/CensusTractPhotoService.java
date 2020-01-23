package info.cinow.service;

import java.util.List;

import info.cinow.model.Photo;

/**
 * CensusTractPhotoService
 */
public interface CensusTractPhotoService {

    public List<Photo> getAllPublicPhotosForTract(Integer censusTractId);

    public Photo getPublicPhotoByIdForTract(Integer censusTractId, Long photoId);
}