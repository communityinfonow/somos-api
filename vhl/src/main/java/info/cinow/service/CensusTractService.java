package info.cinow.service;

import java.util.List;
import java.util.Optional;

import info.cinow.model.CensusTract;
import info.cinow.model.MatchingTract;

/**
 * CensusTractService
 */
public interface CensusTractService {
    public List<CensusTract> getAllCensusTracts();

    public CensusTract getCensusTract(String id);

    public List<MatchingTract> getMatchedTracts(String id);

    public Optional<CensusTract> getCensusTract(double lat, double lng);
}