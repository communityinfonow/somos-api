package info.cinow.repository;

import org.geojson.FeatureCollection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import info.cinow.model.CensusTract;

/**
 * CensusTractDao
 */
public interface CensusTractDao extends CrudRepository<CensusTract, Long> {

    @Query(value = "SELECT ST_AsGeoJSON(geom) FROM bexartracts_2010", nativeQuery = true)
    public FeatureCollection getGeometryCollection();
}