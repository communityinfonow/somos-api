package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
        censusTractDao.findAll().forEach(censusTracts::add);
        return censusTracts;
    }

    @Override

    public FeatureCollection getGeometryCollection() {
        return censusTractDao.getGeometryCollection();
    }
}