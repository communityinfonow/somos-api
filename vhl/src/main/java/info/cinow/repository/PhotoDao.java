package info.cinow.repository;

import org.springframework.data.repository.CrudRepository;

import info.cinow.model.Photo;

/**
 * PhotoDao
 */
public interface PhotoDao extends CrudRepository<Photo, Long> {

}