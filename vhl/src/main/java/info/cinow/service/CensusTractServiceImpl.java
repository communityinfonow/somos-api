package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.CensusTractDto;
import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.repository.CensusTractDao;

/**
 * CensusTractServiceImpl
 */
@Service
public class CensusTractServiceImpl implements CensusTractService {

    @Autowired
    private CensusTractDao censusTractDao;

    @Override
    public List<CensusTractDto> getAllCensusTracts() {
        List<CensusTractDto> censusTracts = new ArrayList<CensusTractDto>();
        censusTractDao.findAll().forEach(tract -> {
            censusTracts.add(CensusTractMapper.toDto(tract));
        });
        return censusTracts;
    }

}