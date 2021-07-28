package academy.mindswap.rentacarapi.persistence.repository;

import academy.mindswap.rentacarapi.persistence.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link CarEntity} persistence operations
 * This interface is implemented by Spring Data JPA
 */
public interface CarRepository extends CrudRepository<CarEntity, Long> {
}
