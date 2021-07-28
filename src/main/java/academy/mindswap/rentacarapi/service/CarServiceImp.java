package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.car.CarDetailsDto;
import academy.mindswap.rentacarapi.command.car.CreateOrUpdateCarDto;
import academy.mindswap.rentacarapi.converter.CarDtoToCarEntityConverter;
import academy.mindswap.rentacarapi.converter.CarEntityToCarDtoConverter;
import academy.mindswap.rentacarapi.exception.CarAlreadyExistsException;
import academy.mindswap.rentacarapi.exception.CarNotFoundException;
import academy.mindswap.rentacarapi.exception.DatabaseCommunicationException;
import academy.mindswap.rentacarapi.persistence.entity.CarEntity;
import academy.mindswap.rentacarapi.persistence.repository.CarRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CarService} implementation
 */
@Service
public class CarServiceImp implements CarService {

    private static final Logger LOGGER = LogManager.getLogger(CarService.class);

    private final CarRepository carRepository;

    public CarServiceImp(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * @see CarService#addNewCar(CreateOrUpdateCarDto)
     */
    @Override
    public CarDetailsDto addNewCar(CreateOrUpdateCarDto carDetails) {

        // Build Car Entity
        CarEntity carEntity = CarDtoToCarEntityConverter.convert(carDetails);
        carEntity.setAvailable(true);

        // Persist car into database
        LOGGER.debug("Persisting new car into database");

        try {
            carRepository.save(carEntity);
        } catch (DataIntegrityViolationException sqlException) {
            LOGGER.error("Car with plate {} already exists in database", carEntity.getPlate(), sqlException);
            throw new CarAlreadyExistsException();
        } catch (Exception e) {
            LOGGER.error("Failed while saving car into database {}", carEntity, e);
            throw new DatabaseCommunicationException(e);
        }

        // Convert to CarDetailsDto and return created car
        return CarEntityToCarDtoConverter.convert(carEntity);
    }

    /**
     * @see CarService#getCarById(long)
     */
    @Override
    public CarDetailsDto getCarById(long carId) {
        LOGGER.debug("Getting car with id {} from database", carId);

        // Get car from database
        CarEntity carEntity = getCarEntityById(carId);

        // Convert to CarDetailsDto and return
        return CarEntityToCarDtoConverter.convert(carEntity);
    }

    /**
     * @see CarService#getCarsList()
     */
    @Override
    public List<CarDetailsDto> getCarsList() {
        // Convert list items from CarEntity to CarDetailsDto
        List<CarDetailsDto> carsListResponse = new ArrayList<>();

        LOGGER.debug("Getting all cars from database");
        try {

            for (CarEntity car : carRepository.findAll()) {
                carsListResponse.add(CarEntityToCarDtoConverter.convert(car));
            }
        } catch (Exception e) {
            LOGGER.error("Failed getting all cars from database", e);
            throw new DatabaseCommunicationException(e);
        }

        return carsListResponse;
    }

    /**
     * @see CarService#deleteCar(long)
     */
    @Override
    public void deleteCar(long carId) {
        LOGGER.debug("Verifying if car with id {} exists in database", carId);

        CarEntity carEntity = getCarEntityById(carId);

        LOGGER.debug("Removing car with id {} from database", carId);

        try {
            carRepository.delete(carEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while deleting car with id {} from database", carId, e);
            throw new DatabaseCommunicationException(e);
        }
    }

    /**
     * @see CarService#updateCarDetails(long, CreateOrUpdateCarDto)
     */
    @Override
    public CarDetailsDto updateCarDetails(long carId, CreateOrUpdateCarDto carDetails) {
        LOGGER.debug("Verifying if user with id {} exists in database", carId);

        // Get car if it exists
        CarEntity carEntity = getCarEntityById(carId);

        // Update data with carDetails received into carEntity
        carEntity.setBrand(carDetails.getBrand());
        carEntity.setModelDescription(carDetails.getModelDescription());
        carEntity.setCarSegment(carDetails.getCarSegment());
        carEntity.setPlate(carDetails.getPlate());
        carEntity.setDateOfPurchase(carDetails.getDateOfPurchase());

        // Save changes
        LOGGER.debug("Updating car with id {} in database with new data", carId);

        try {
            carEntity = carRepository.save(carEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while updating car with id {} in database with new data - {}", carId, carEntity, e);
            throw new DatabaseCommunicationException(e);
        }
        // Convert to CarDetailsDto and return updated car
        return CarEntityToCarDtoConverter.convert(carEntity);
    }

    protected CarEntity getCarEntityById(long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> {
                    LOGGER.error("The car with id {} does not exist in the database", carId);
                    return new CarNotFoundException();
                });
    }
}
