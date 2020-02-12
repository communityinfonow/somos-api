package info.cinow.service;

import info.cinow.model.Indicator;
import info.cinow.model.IndicatorData;

/**
 * IndicatorService
 */
public interface IndicatorService {

    public IndicatorData getDataByIndicatorGeography(String censusTractId, Long indicatorId);

    public Indicator getLifeExpectancyIndicator();
}