package info.cinow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.cinow.model.CensusTract;
import info.cinow.model.IndicatorData;
import info.cinow.model.IndicatorTractId;

/**
 * IndicatorDao
 */
public interface IndicatorDataDao extends CrudRepository<IndicatorData, IndicatorTractId> {

    @Query(value = "SELECT max(value) from indicator_data WHERE indicator_id = :indicatorId", nativeQuery = true)
    public Double getMaxValueByIndicatorId(@Param("indicatorId") Long indicatorid);

}