package academy.mindswap.rentacarapi.persistence.repository;

import academy.mindswap.rentacarapi.persistence.entity.RentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

/**
 * Repository for {@link RentEntity} persistence operations.
 * This interface is implemented by Spring Data JPA
 */
public interface RentRepository extends CrudRepository<RentEntity, Long> {

    /**
     * Check if car is available between dates
     * @param carId
     * @param beginDate
     * @param endDate
     * @return true if available and false if not
     */
    @Query(value = "SELECT CASE WHEN EXISTS( " +
            "SELECT *\n" +
            "FROM rents t1\n" +
            "WHERE t1.car_id = :carId\n" +
            "AND (( :beginDate BETWEEN t1.expected_begin_date AND t1.expected_end_date)\n" +
            "OR ( :endDate BETWEEN t1.expected_begin_date AND t1.expected_end_date))\n" +
            ") THEN 'FALSE' ELSE 'TRUE' END",
            nativeQuery = true
    )
    boolean isCarAvailableBetweenDates(@Param("carId") long carId,
                                  @Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate);


    @Query(value = "SELECT * FROM rents WHERE rent_id = :rentId AND user_id = :userId",
            nativeQuery = true)
    Optional<RentEntity> findByRentIdAndUserId(@Param("rentId") long rentId,
                                               @Param("userId") long userId);
}
