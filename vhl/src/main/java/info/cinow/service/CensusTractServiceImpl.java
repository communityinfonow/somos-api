package info.cinow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.CensusTract;
import info.cinow.model.MatchingTract;
import info.cinow.repository.CensusTractDao;
import info.cinow.repository.MatchedCensusTractDao;

/**
 * CensusTractServiceImpl
 */
@Service
public class CensusTractServiceImpl implements CensusTractService {

    @Autowired
    private CensusTractDao censusTractDao;

    @Autowired
    private MatchedCensusTractDao matchedCensusTractDao;

    @Override
    public List<CensusTract> getAllCensusTracts() {
        List<CensusTract> censusTracts = new ArrayList<CensusTract>();
        censusTractDao.findAll().forEach(tract -> {
            censusTracts.add(tract);
        });
        return censusTracts;
    }

    @Override
    public CensusTract getCensusTract(String id) {
        return censusTractDao.findById(id).orElse(null);
    }

    @Override
    public List<MatchingTract> getMatchedTracts(String id) {
        return this.matchedCensusTractDao.findMatchingTractsByParentId(id).stream().collect(Collectors.toList());
    }

    @Override
    public Optional<CensusTract> getCensusTract(double lat, double lng) {
        return this.censusTractDao.getContainingTract(lng, lat);
    }
}