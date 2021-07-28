package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.rent.CreateOrUpdateRentDto;
import academy.mindswap.rentacarapi.command.rent.RentDetailsDto;

import java.util.List;

/**
 * Common interface for rent service, provides methods to manage rents
 */
public interface RentService {

    /**
     * Add new rent
     * @param createRentDto
     * @return {@link RentDetailsDto}
     */
    RentDetailsDto addNewRent(CreateOrUpdateRentDto createRentDto);

    /**
     * Get rent by rentId
     * @param rentId
     * @param userId
     * @return {@link RentDetailsDto}
     */
    RentDetailsDto getRentById(long rentId, long userId);

    /**
     * Get all rents
     * @return
     */
    List<RentDetailsDto> getRentsList();

    /**
     * Deliver car to the customer
     * @param rentId
     * @return {@link RentDetailsDto}
     */
    RentDetailsDto deliverCar(long rentId);

    /**
     * Costumer returns the car
     * @param rentId
     * @return {@link RentDetailsDto}
     */
    RentDetailsDto returnCar(long rentId);

    /**
     * Delete rent reservation
     * @param rentId
     */
    void deleteRent(long rentId);
}
