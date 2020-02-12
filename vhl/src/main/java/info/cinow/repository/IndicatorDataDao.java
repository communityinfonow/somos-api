package info.cinow.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.cinow.model.Indicator;
import info.cinow.model.IndicatorData;
import info.cinow.model.IndicatorTractId;

/**
 * IndicatorDao
 */
public interface IndicatorDataDao extends CrudRepository<IndicatorData, IndicatorTractId> {

    @Query(value = "SELECT max(value) from indicator_data WHERE indicator_id = :indicatorId", nativeQuery = true)
    public Double getMaxValueByIndicatorId(@Param("indicatorId") Long indicatorid);

    @Query(value = "SELECT min(value) from indicator_data WHERE indicator_id = :indicatorId", nativeQuery = true)
    public Double getMinValueByIndicatorId(@Param("indicatorId") Long indicatorid);

    @Query(value = "select a from IndicatorData a left join fetch a.censusTract where a.id.indicatorId = :indicatorId")
    public Set<IndicatorData> findByIdIndicatorId(@Param("indicatorId") Long indicatorId);

}