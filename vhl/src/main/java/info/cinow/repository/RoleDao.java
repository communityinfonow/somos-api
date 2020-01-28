package info.cinow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.cinow.model.Role;

/**
 * RoleDao
 */
public interface RoleDao extends CrudRepository<Role, Long> {
    @Query(value = "SELECT * FROM ROLE WHERE NAME = :name", nativeQuery = true)
    public Optional<Role> findByName(@Param("name") String name);
}