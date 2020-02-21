package info.cinow.service;

import java.util.List;

import info.cinow.model.Photo;

/**
 * CensusTractPhotoService
 */
public interface CensusTractPhotoService {

    public List<Photo> getAllPublicPhotosForTract(String censusTractId);

    public Photo getPublicPhotoByIdForTract(String censusTractId, Long photoId);
}