package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.rent.CreateOrUpdateRentDto;
import academy.mindswap.rentacarapi.command.rent.RentDetailsDto;
import academy.mindswap.rentacarapi.converter.RentDtoToRentEntityConverter;
import academy.mindswap.rentacarapi.converter.RentEntityToRentDtoConverter;
import academy.mindswap.rentacarapi.error.ErrorMessages;
import academy.mindswap.rentacarapi.exception.*;
import academy.mindswap.rentacarapi.persistence.entity.CarEntity;
import academy.mindswap.rentacarapi.persistence.entity.RentEntity;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;
import academy.mindswap.rentacarapi.persistence.repository.RentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * An {@link RentService} implementation
 */
@Service
public class RentServiceImp implements RentService {

    private static final Logger LOGGER = LogManager.getLogger(RentServiceImp.class);

    private final RentRepository rentRepository;
    private final UserServiceImp userServiceImp;
    private final CarServiceImp carServiceImp;

    public RentServiceImp(RentRepository rentRepository, UserServiceImp userServiceImp, CarServiceImp carServiceImp) {
        this.rentRepository = rentRepository;
        this.userServiceImp = userServiceImp;
        this.carServiceImp = carServiceImp;
    }

    /**
     * @see RentService#addNewRent(CreateOrUpdateRentDto)
     */
    @Override
    public RentDetailsDto addNewRent(CreateOrUpdateRentDto createRentDto) {

        LOGGER.debug("Getting car with id {}", createRentDto.getCarId());

        // Get car from database
        CarEntity carEntity = carServiceImp.getCarEntityById(createRentDto.getCarId());

        LOGGER.debug("Verifying if car id {} will be available between the given dates {} - {}",
                createRentDto.getCarId(),
                createRentDto.getExpectedBeginDate(),
                createRentDto.getExpectedEndDate());

        // Verify if the car will be available for the requested dates
        boolean carAvailable = rentRepository.isCarAvailableBetweenDates(
                createRentDto.getCarId(),
                createRentDto.getExpectedBeginDate(),
                createRentDto.getExpectedEndDate());

        if (!carAvailable) {
            LOGGER.info("Car will be unavailable between dates {} - {}",
                    createRentDto.getExpectedBeginDate(),
                    createRentDto.getExpectedEndDate());

            throw new CarNotAvailableException();
        }

        LOGGER.debug("Getting user with id {}", createRentDto.getUserId());

        // Get user from database
        UserEntity userEntity = userServiceImp.getUserEntityById(createRentDto.getUserId());

        // Convert to RentEntity
        RentEntity rentEntity = RentDtoToRentEntityConverter.convert(createRentDto);

        // Set car and user entities to RentEntity
        rentEntity.setCarEntity(carEntity);
        rentEntity.setUserEntity(userEntity);

        // Calculate and set the expected price
        rentEntity.setExpectedPrice(calculatePrice(createRentDto.getExpectedBeginDate(),
                createRentDto.getExpectedEndDate(), carEntity.getCarSegment().getDailyPrice()));

        LOGGER.debug("Saving rent into database");

        // Save new rent reservation
        try {
            rentRepository.save(rentEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while saving rent into database {}", rentEntity, e);
            throw new DatabaseCommunicationException(e);
        }
        return RentEntityToRentDtoConverter.convert(rentEntity);
    }

    /**
     * @see RentService#getRentById(long, long)
     */
    @Override
    public RentDetailsDto getRentById(long rentId, long userId) {
        LOGGER.debug("Getting rent with id {} from database", rentId);

        // Get the car from database
        RentEntity rentEntity = getRentEntityById(rentId);

        return RentEntityToRentDtoConverter.convert(rentEntity);
    }

    /**
     * @see RentService#getRentsList()
     */
    @Override
    public List<RentDetailsDto> getRentsList() {
        LOGGER.debug("Getting all rents from database");

        // Convert the list from RentEntity to RentDetailsDto
        List<RentDetailsDto> rentListDto = new ArrayList<>();

        try {
            for (RentEntity rent : rentRepository.findAll()) {
                rentListDto.add(RentEntityToRentDtoConverter.convert(rent));
            }
        } catch (Exception e) {
            LOGGER.error("Failed getting all rents from database", e);
            throw new DatabaseCommunicationException(e);
        }

        return rentListDto;
    }

    /**
     * @see RentService#deliverCar(long)
     */
    @Override
    public RentDetailsDto deliverCar(long rentId) {
        LOGGER.debug("Getting rent with id {} from database", rentId);

        // Get rent from database
        RentEntity rentEntity = getRentEntityById(rentId);

        LOGGER.debug("Checking if car was already picked");

        // Verify if the car is available
        if (!rentEntity.getCarEntity().isAvailable()) {
            LOGGER.error("User {} already picked the car {} in the rent {}",
                    rentEntity.getUserEntity().getUserId(),
                    rentEntity.getCarEntity().getCarId(),
                    rentId);
            throw new CarNotAvailableException();
        }

        // Set the beginDate
        rentEntity.setBeginDate(new Date());

        // Set car as unavailable
        rentEntity.getCarEntity().setAvailable(false);

        LOGGER.info("Saving changes into database");

        // Save the changes in the database
        try {
            rentRepository.save(rentEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while updating rent status in database", e);
            throw new DatabaseCommunicationException(e);
        }

        return RentEntityToRentDtoConverter.convert(rentEntity);
    }

    /**
     * @see RentService#returnCar(long)
     */
    @Override
    public RentDetailsDto returnCar(long rentId) {
        LOGGER.debug("Getting rent with id {} from database", rentId);

        // Get rent from database
        RentEntity rentEntity = getRentEntityById(rentId);

        // Verify if the car wasn't delivered and if it was already returned
        if (Objects.isNull(rentEntity.getBeginDate()) || Objects.nonNull(rentEntity.getEndDate())) {
            LOGGER.error("Current status (Picked: {}, Returned: {}) of rent {} doesn't allow car to be returned",
                    Objects.nonNull(rentEntity.getBeginDate()),
                    Objects.nonNull(rentEntity.getEndDate()),
                    rentId);
            throw new InvalidRentStatusException(ErrorMessages.CAN_NOT_RETURN_CAR);
        }

        // Set the endDate
        rentEntity.setEndDate(new Date());

        // Calculate and set the finalPrice
        rentEntity.setFinalPrice(calculatePrice(rentEntity.getBeginDate(),
                rentEntity.getEndDate(), rentEntity.getCarEntity().getCarSegment().getDailyPrice()));

        // Set the car back to available
        rentEntity.getCarEntity().setAvailable(true);

        LOGGER.info("Saving changes into database");

        // Save the changes
        try {
            rentRepository.save(rentEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while updating rent status in database", e);
            throw new DatabaseCommunicationException(e);
        }
        return RentEntityToRentDtoConverter.convert(rentEntity);
    }

    /**
     * @see RentService#deleteRent(long)
     */
    @Override
    public void deleteRent(long rentId) {
        LOGGER.debug("Getting rent with id {} from database", rentId);

        // Get rent from database
        RentEntity rentEntity = getRentEntityById(rentId);

        // Verify if the car was already delivered
        if (Objects.nonNull(rentEntity.getBeginDate())) {
            LOGGER.error("Rent {} cannot be deleted after the car being picked", rentId);
            throw new InvalidRentStatusException(ErrorMessages.CAN_NOT_DELETE_CAR_ALREADY_DELIVERED);
        }

        LOGGER.info("Deleting rent with id {} from database", rentId);

        // Delete rent from database
        try {
            rentRepository.delete(rentEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while deleting rent with id {} from database", rentId, e);
            throw new DatabaseCommunicationException(e);
        }
    }

    /**
     * Helper method to calculate the price considering the begin date, end date and the daily price
     *
     * @param beingDate
     * @param endDate
     * @param dailyPrice
     * @return {@link BigDecimal} the total price
     */
    private BigDecimal calculatePrice(Date beingDate, Date endDate, BigDecimal dailyPrice) {
        // Convert to LocalDate
        LocalDate beginLocalDate = LocalDate.ofInstant(beingDate.toInstant(), ZoneId.systemDefault());
        LocalDate endLocalDate = LocalDate.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        // Get difference between beginDate & endDate in days
        long numOfDaysBetween = ChronoUnit.DAYS.between(beginLocalDate, endLocalDate);

        // Calculate and return the totalCost
        return dailyPrice.multiply(BigDecimal.valueOf(numOfDaysBetween != 0L ? numOfDaysBetween : 1L));
    }

    protected RentEntity getRentEntityById(long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> {
                    LOGGER.error("The rent with id {} does not exist in the database", rentId);
                    return new RentNotFoundException();
                });
    }
}
