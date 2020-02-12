package info.cinow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.mapper.IndicatorDataMapper;
import info.cinow.model.Indicator;
import info.cinow.model.IndicatorData;
import info.cinow.model.IndicatorTractId;
import info.cinow.repository.IndicatorDao;
import info.cinow.repository.IndicatorDataDao;

/**
 * IndicatorServiceImpl
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorDataDao indicatorDataDao;

    @Autowired
    private IndicatorDao indicatorDao;

    @Override
    public IndicatorData getDataByIndicatorGeography(String censusTractId, Long indicatorId) {
        IndicatorTractId id = new IndicatorTractId();
        id.setGid(censusTractId);
        id.setIndicatorId(indicatorId);

        IndicatorData data = this.indicatorDataDao.findById(id).orElse(null);
        if (data.getIndicator().getValueType().getType().equals("percent")) {
            data.setMaxValue(100.0);
        } else {
            data.setMaxValue(this.indicatorDataDao.getMaxValueByIndicatorId(indicatorId));
        }

        return data;
    }

    @Override
    public Indicator getLifeExpectancyIndicator() {
        return this.indicatorDao.findLifeExpectancyIndicator();
    }

}