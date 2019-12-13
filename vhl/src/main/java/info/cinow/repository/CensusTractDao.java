package info.cinow.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.cinow.model.CensusTract;

/**
 * CensusTractDao
 */
public interface CensusTractDao extends CrudRepository<CensusTract, Integer> {

    @Query(value = "SELECT gid FROM (SELECT gid,  ST_CONTAINS(GEOM\\:\\:geometry, ST_SetSRID(ST_MakePoint(:longitude, :latitude),4326)) AS CONTAINING FROM bexartracts_2010) ss WHERE ss.CONTAINING = true", nativeQuery = true)
    public Long getContainingTract(@Param("longitude") double longitude, @Param("latitude") double latitude);
}