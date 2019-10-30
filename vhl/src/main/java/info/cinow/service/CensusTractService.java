package info.cinow.service;

import java.util.List;

import org.geojson.FeatureCollection;

import info.cinow.model.CensusTract;

/**
 * CensusTractService
 */
public interface CensusTractService {
    public List<CensusTract> getAllCensusTracts();

    public FeatureCollection getGeometryCollection();
}