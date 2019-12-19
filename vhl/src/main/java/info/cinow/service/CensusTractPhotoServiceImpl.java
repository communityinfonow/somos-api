package info.cinow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import info.cinow.model.Photo;
import info.cinow.repository.CensusTractPhotoDao;

/**
 * CensusTractPhotoService
 */
public class CensusTractPhotoServiceImpl implements CensusTractPhotoService {

    @Autowired
    CensusTractPhotoDao dao;

    public List<Photo> getAllPhotosForTract(Integer tractId) {
        return this.dao.findByCensusTractId(tractId);
    }

    public Photo getPhotoByIdForTract(Integer censusTractId, Long photoId) {
        return this.dao.findByCensusTractPhotoId(censusTractId, photoId);
    }
}