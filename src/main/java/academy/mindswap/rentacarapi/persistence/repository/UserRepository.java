package academy.mindswap.rentacarapi.persistence.repository;

import academy.mindswap.rentacarapi.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for {@link UserEntity} persistence operations
 * This interface is implemented by Spring Data JPA
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     * Get user by email
     * @param email
     * @return
     */
    @Query(value = "SELECT * FROM users WHERE email = :email",
            nativeQuery = true)
    Optional<UserEntity> findByEmail(@Param("email") String email);

   /* @Query(value = "SELECT * FROM users WHERE email = :email AND password = :password",
            nativeQuery = true)
    Optional<UserEntity> findByEmailAndPassword(@Param("email") String email,
                                                @Param("password") String password);*/
}
