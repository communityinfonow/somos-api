package info.cinow.service;

import java.util.List;

import info.cinow.model.CensusTract;

/**
 * CensusTractService
 */
public interface CensusTractService {
    public List<CensusTract> getAllCensusTracts();

    public CensusTract getCensusTract(Integer id);
}