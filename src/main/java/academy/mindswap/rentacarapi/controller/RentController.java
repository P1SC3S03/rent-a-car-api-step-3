package academy.mindswap.rentacarapi.controller;

import academy.mindswap.rentacarapi.command.rent.CreateOrUpdateRentDto;
import academy.mindswap.rentacarapi.command.rent.RentDetailsDto;
import academy.mindswap.rentacarapi.persistence.entity.RentEntity;
import academy.mindswap.rentacarapi.service.RentServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller responsible for {@link RentEntity} related CRUD operations
 */
@RestController
@RequestMapping("/api/rents")
public class RentController {

    private static final Logger LOGGER = LogManager.getLogger(RentController.class);

    private RentServiceImp rentService;

    public RentController(RentServiceImp rentService) {
        this.rentService = rentService;
    }

    /**
     * Create rent
     *
     * @param createRentDto the new rent data
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> createRent(@Valid @RequestBody CreateOrUpdateRentDto createRentDto, BindingResult bindingResult) {
        LOGGER.info("Request to create new rent reservation - {}", createRentDto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Request to create new rent failed. RentDto has errors - {}", bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        RentDetailsDto rentDetails = rentService.addNewRent(createRentDto);

        LOGGER.info("Rent created successfully. Retrieving created rent with id {}", rentDetails.getRentId());

        return new ResponseEntity<>(rentDetails, HttpStatus.CREATED);
    }

    /**
     * Get rent by id
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @GetMapping("/{rentId}/user/{userId}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable long userId, @PathVariable long rentId) {
        LOGGER.info("Request to get rent with id {}", rentId);

        RentDetailsDto rentDetails = rentService.getRentById(rentId, userId);

        LOGGER.info("Retrieving rent with id {}", rentId);

        return new ResponseEntity<>(rentDetails, HttpStatus.OK);
    }

    /**
     * Get rent list
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getRentList() {
        LOGGER.info("Request to get rents list");

        List<RentDetailsDto> rentDetailsDtoList = rentService.getRentsList();

        LOGGER.info("Retrieving rents list");

        return new ResponseEntity<>(rentDetailsDtoList, HttpStatus.OK);
    }

    /**
     * Deliver car
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @PutMapping("/{rentId}/deliver")
    public ResponseEntity<RentDetailsDto> deliverCar(@PathVariable long rentId) {
        LOGGER.info("Request to deliver car from rent with id {}", rentId);

        RentDetailsDto rentDetails = rentService.deliverCar(rentId);

        LOGGER.info("Deliver processed successfully. Retrieving updated rent with id {}", rentId);

        return new ResponseEntity<>(rentDetails, HttpStatus.OK);
    }

    /**
     * Return car
     *
     * @param rentId the rentId
     * @return the response entity
     */
    @PutMapping("/{rentId}/return")
    public ResponseEntity<RentDetailsDto> returnCar(@PathVariable long rentId) {
        LOGGER.info("Request to return car from rent with id {}", rentId);

        RentDetailsDto rentDetailsDto = rentService.returnCar(rentId);

        LOGGER.info("Return processed successfully. Retrieving updated rent with id {}", rentId);

        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    /**
     * Delete rent
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @DeleteMapping("/{rentId}")
    public ResponseEntity<HttpStatus> deleteRent(@PathVariable long rentId) {
        LOGGER.info("Request to delete rent with id {}", rentId);

        rentService.deleteRent(rentId);

        LOGGER.info("Rent with id {} deleted successfully", rentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
