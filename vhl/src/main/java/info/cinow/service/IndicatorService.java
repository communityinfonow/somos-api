package info.cinow.service;

import java.util.List;

import info.cinow.dto.IndicatorDataSet;
import info.cinow.model.Indicator;
import info.cinow.model.IndicatorData;

/**
 * IndicatorService
 */
public interface IndicatorService {

    public IndicatorData getDataByIndicatorGeography(String censusTractId, Long indicatorId);

    public Indicator getLifeExpectancyIndicator();

    public List<Indicator> getAllIndicators();

    public IndicatorDataSet getDataByIndicatorId(Long indicatorId);
}