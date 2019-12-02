package info.cinow.authentication;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * UserDao
 */
public interface UserDao extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM USER_TABLE WHERE EMAIL_ADDRESS = :emailAddress", nativeQuery = true)
    public Optional<User> findByEmailAddress(@Param("emailAddress") String emailAddress);
}