package info.cinow.service;

import java.util.List;
import java.util.Optional;

import info.cinow.model.CensusTract;

/**
 * CensusTractService
 */
public interface CensusTractService {
    public List<CensusTract> getAllCensusTracts();

    public CensusTract getCensusTract(Integer id);

    public List<CensusTract> getMatchedTracts(Integer id);

    public Optional<CensusTract> getCensusTract(double lat, double lng);
}