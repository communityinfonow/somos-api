package info.cinow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.Photo;
import info.cinow.repository.CensusTractPhotoDao;

/**
 * CensusTractPhotoService
 */
@Service
public class CensusTractPhotoServiceImpl implements CensusTractPhotoService {

    @Autowired
    CensusTractPhotoDao dao;

    public List<Photo> getAllPhotosForTract(Integer censusTractId) {
        return this.dao.findByCensusTractId(censusTractId);
    }

    public Photo getPhotoByIdForTract(Integer censusTractId, Long photoId) {
        return this.dao.findByCensusTractPhotoId(censusTractId, photoId);
    }
}