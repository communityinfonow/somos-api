package info.cinow.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.cinow.model.MatchingTract;

/**
 * CensusTractDao
 */
public interface MatchedCensusTractDao extends CrudRepository<MatchingTract, String> {

    @Query(value = "SELECT * FROM MATCHING_TRACT WHERE PARENT_TRACT_GID = :parentId", nativeQuery = true)
    public Collection<MatchingTract> findMatchingTractsByParentId(@Param("parentId") String parentId);

}