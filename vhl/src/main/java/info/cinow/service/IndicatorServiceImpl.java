package info.cinow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.dto.IndicatorDataSet;
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
        data.setMaxValue(this.determineMaxValue(data.getIndicator()));
        return data;
    }

    @Override
    public List<Indicator> getAllIndicators() {
        List<Indicator> indicators = new ArrayList<Indicator>();
        this.indicatorDao.findAll().forEach(indicators::add);
        return indicators;
    }

    @Override
    public Indicator getLifeExpectancyIndicator() {
        return this.indicatorDao.findLifeExpectancyIndicator();
    }

    @Override
    public IndicatorDataSet getDataByIndicatorId(Long indicatorId) {
        IndicatorDataSet dataset = new IndicatorDataSet();
        try {
            Indicator indicator = this.getIndicatorById(indicatorId).get();
            double maxValue = this.determineMaxValue(indicator);
            double minValue = this.determineMinValue(indicator);
            Set<IndicatorData> indicatorData = this.indicatorDataDao.findByIdIndicatorId(indicatorId);
            dataset.setMaxValue(maxValue);
            dataset.setMinValue(minValue);
            dataset.setIndicatorData(indicatorData);
        } catch (NoSuchElementException e) {
            return dataset;
        }

        return dataset;
    }

    public Optional<Indicator> getIndicatorById(Long indicatorId) {
        return this.indicatorDao.findById(indicatorId);
    }

    private Double determineMaxValue(Indicator indicator) {
        Double maxValue = this.indicatorDataDao.getMaxValueByIndicatorId(indicator.getId());
        if (!indicator.getValueType().equals(null) && indicator.getValueType().getType().equals("percent")) {
            return 100.0;
        } else {
            return maxValue;
        }
    }

    private Double determineMinValue(Indicator indicator) {
        Double minValue = this.indicatorDataDao.getMinValueByIndicatorId(indicator.getId());
        if (indicator.getValueType().getType().equals("percent")) {
            return 0.0;
        } else {
            return minValue;
        }
    }

}