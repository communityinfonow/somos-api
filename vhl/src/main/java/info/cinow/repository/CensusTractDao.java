package info.cinow.repository;

import org.springframework.data.repository.CrudRepository;

import info.cinow.model.CensusTract;

/**
 * CensusTractDao
 */
public interface CensusTractDao extends CrudRepository<CensusTract, Integer> {
}