package info.cinow.authentication;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * PrivilegeDao
 */
public interface PrivilegeDao extends CrudRepository<Privilege, Long> {

    @Query(value = "SELECT * FROM PRIVILEGE WHERE NAME = :name", nativeQuery = true)
    public Optional<Privilege> findByName(@Param("name") String name);

}