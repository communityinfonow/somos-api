package info.cinow.service;

import java.util.List;
import java.util.Optional;

import info.cinow.model.CensusTract;

/**
 * CensusTractService
 */
public interface CensusTractService {
    public List<CensusTract> getAllCensusTracts();

    public CensusTract getCensusTract(String id);

    public List<CensusTract> getMatchedTracts(String id);

    public Optional<CensusTract> getCensusTract(double lat, double lng);
}