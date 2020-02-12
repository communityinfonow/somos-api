package info.cinow.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import info.cinow.model.Indicator;

/**
 * IndicatorDao
 */
public interface IndicatorDao extends CrudRepository<Indicator, Long> {

    @Query(value = "SELECT * from indicator WHERE is_life_expectancy = true ", nativeQuery = true)
    public Indicator findLifeExpectancyIndicator();

}