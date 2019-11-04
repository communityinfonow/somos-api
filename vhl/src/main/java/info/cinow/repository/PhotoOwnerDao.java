package info.cinow.repository;

import org.springframework.data.repository.CrudRepository;

import info.cinow.model.PhotoOwner;

/**
 * PhotoOwnerDao
 */
public interface PhotoOwnerDao extends CrudRepository<PhotoOwner, Long> {

}