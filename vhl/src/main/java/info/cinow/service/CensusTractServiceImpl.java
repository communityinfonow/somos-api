package info.cinow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.CensusTract;
import info.cinow.repository.CensusTractDao;

/**
 * CensusTractServiceImpl
 */
@Service
public class CensusTractServiceImpl implements CensusTractService {

    @Autowired
    private CensusTractDao censusTractDao;

    @Override
    public List<CensusTract> getAllCensusTracts() {
        List<CensusTract> censusTracts = new ArrayList<CensusTract>();
        censusTractDao.findAll().forEach(tract -> {
            censusTracts.add(tract);
        });
        return censusTracts;
    }

    @Override
    public CensusTract getCensusTract(Integer id) {
        return censusTractDao.findById(id).orElse(null);
    }

    @Override
    public List<CensusTract> getMatchedTracts(Integer id) {
        CensusTract tract = censusTractDao.findById(id).orElse(null);

        return tract.getMatchingCensusTracts().stream().map(matchingTracts -> matchingTracts.getChildTract())
                .collect(Collectors.toList());
    }
}