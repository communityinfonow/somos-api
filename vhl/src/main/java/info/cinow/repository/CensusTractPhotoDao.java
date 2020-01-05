package info.cinow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import info.cinow.model.Photo;

/**
 * CensusTractPhotoDao
 */
@Component("censusTractPhotoDao")
public interface CensusTractPhotoDao extends CrudRepository<Photo, Integer> {

    @Query(value = "select * from photo where gid = :censusTractId and approved = true", nativeQuery = true)
    public List<Photo> findPublicByCensusTractId(@Param("censusTractId") Integer censusTractId);

    @Query(value = "select * from photo where id = :photoId and gid = :censusTractId and approved = true", nativeQuery = true)
    public Photo findPublicByCensusTractPhotoId(@Param("censusTractId") Integer censusTractId,
            @Param("photoId") Long photoId);
}