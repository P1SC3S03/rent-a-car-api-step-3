package academy.mindswap.rentacarapi.service;


import academy.mindswap.rentacarapi.command.car.CarDetailsDto;
import academy.mindswap.rentacarapi.command.car.CreateOrUpdateCarDto;

import java.util.List;

/**
 * Common interface for car services, provides methods to manage cars
 */
public interface CarService {

    /**
     * Create new car
     *
     * @param carDetails {@link CreateOrUpdateCarDto}
     * @return created car {@link CarDetailsDto}
     */
    CarDetailsDto addNewCar(CreateOrUpdateCarDto carDetails);

    /**
     * Get car by id
     *
     * @param carId car we want to find
     * @return {@link CarDetailsDto}
     */
    CarDetailsDto getCarById(long carId);

    /**
     * Get a list with all cars
     *
     * @return a list with {@link CarDetailsDto}
     */
    List<CarDetailsDto> getCarsList();

    /**
     * Delete car by carID
     *
     * @param carId car we want to delete
     */
    void deleteCar(long carId);

    /**
     * Update car details
     *
     * @param carId      car to be updated
     * @param carDetails data to update
     * @return car details updated
     */
    CarDetailsDto updateCarDetails(long carId, CreateOrUpdateCarDto carDetails);

}
