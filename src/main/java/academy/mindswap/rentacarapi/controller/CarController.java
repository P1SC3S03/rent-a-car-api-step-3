package academy.mindswap.rentacarapi.controller;

import academy.mindswap.rentacarapi.command.car.CarDetailsDto;
import academy.mindswap.rentacarapi.command.car.CreateOrUpdateCarDto;
import academy.mindswap.rentacarapi.persistence.entity.CarEntity;
import academy.mindswap.rentacarapi.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller responsible for {@link CarEntity} related CRUD operations
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private static Logger LOGGER = LogManager.getLogger(CarController.class);

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Create car
     *
     * @param createOrUpdateCarDto the car data
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto, BindingResult bindingResult) {
        LOGGER.info("Request to create new car - {}", createOrUpdateCarDto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Request to create new car failed. CarDto has errors - {}", bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        CarDetailsDto carDetails = carService.addNewCar(createOrUpdateCarDto);

        LOGGER.info("Car created successfully. Retrieving created car with id {}", carDetails.getCarId());

        return new ResponseEntity<>(carDetails, HttpStatus.CREATED);
    }

    /**
     * Get car be id
     *
     * @param carId the car id
     * @return the response entity
     */
    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> getCarById(@PathVariable long carId) {
        LOGGER.info("Request to get car by id - {}", carId);

        CarDetailsDto carDetails = carService.getCarById(carId);

        LOGGER.info("Retrieving car with id - {}", carDetails.getCarId());

        return new ResponseEntity<>(carDetails, HttpStatus.OK);
    }

    /**
     * Get all cars
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<CarDetailsDto>> getCarsList() {
        LOGGER.info("Request to get cars list");

        List<CarDetailsDto> usersList = carService.getCarsList();

        LOGGER.info("Retrieving cars list");

        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    /**
     * Update car
     *
     * @param carId   the car id
     * @param createOrUpdateCarDto the data to update
     * @return the response entity
     */
    @PutMapping("/{carId}")
    public ResponseEntity<?> updateCar(@PathVariable long carId,
                                                   @Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto,
                                                   BindingResult bindingResult) {
        LOGGER.info("Request to update car with id {} - {}", carId, createOrUpdateCarDto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Failed to update car with id {}. CarDto has errors - {}", carId, bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        CarDetailsDto carDetailsDto = carService.updateCarDetails(carId, createOrUpdateCarDto);

        LOGGER.info("Car with id {} updated successfully. Retrieving updated data.", carId);

        return new ResponseEntity<>(carDetailsDto, HttpStatus.OK);
    }

    /**
     * Delete car
     *
     * @param carId the car id
     * @return the response entity
     */
    @DeleteMapping("/{carId}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable long carId) {
        LOGGER.info("Request to delete car with id {}", carId);

        carService.deleteCar(carId);

        LOGGER.info("Car with id {} deleted successfully", carId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
