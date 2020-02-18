package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.MapBreak;
import info.cinow.repository.MapBreakDao;

/**
 * MapBreaksServiceImpl
 */
@Service
public class MapBreaksServiceImpl implements MapBreakService {

    @Autowired
    private MapBreakDao mapBreakDao;

    @Override
    public List<MapBreak> getMapBreaks() {
        List<MapBreak> mapBreaks = new ArrayList<MapBreak>();
        this.mapBreakDao.findAll().forEach(mapBreaks::add);
        return mapBreaks;
    }

}