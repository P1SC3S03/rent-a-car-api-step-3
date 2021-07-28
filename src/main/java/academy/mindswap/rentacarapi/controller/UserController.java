package academy.mindswap.rentacarapi.controller;

import academy.mindswap.rentacarapi.command.user.CreateOrUpdateUserDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;
import academy.mindswap.rentacarapi.service.UserServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * REST controller responsible for {@link UserEntity} related CRUD operations
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    /**
     * Create new user
     *
     * @param createUserDto new user data
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> registration(@Valid @RequestBody CreateOrUpdateUserDto createUserDto, BindingResult bindingResult) {
        LOGGER.info("Request to create new user - {}", createUserDto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Request to create new user failed. UserDto has errors - {}", bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        UserDetailsDto userDetailsDto = userService.createUser(createUserDto);

        LOGGER.info("User created successfully. Retrieving created user with id {}", userDetailsDto.getUserId());

        return new ResponseEntity<>(userDetailsDto, HttpStatus.CREATED);
    }

    /**
     * Get user by id
     *
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long userId) {
        LOGGER.info("Request to get user with id {}", userId);

        UserDetailsDto userDetailsDto = userService.getUserById(userId);

        LOGGER.info("Retrieving user with id {}", userId);

        return new ResponseEntity<>(userDetailsDto, OK);
    }

    /**
     * Get all users
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        LOGGER.info("Request to get users list");

        List<UserDetailsDto> usersList = userService.getUsersList();

        LOGGER.info("Retrieving users list");

        return new ResponseEntity<>(usersList, OK);
    }

    /**
     * Update user
     *
     * @param userId        the user id
     * @param updateUserDto the data to update
     * @return the response entity
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId,
                                        @Valid @RequestBody CreateOrUpdateUserDto updateUserDto,
                                        BindingResult bindingResult) {

        LOGGER.info("Request to update user with id {} - {}", userId, updateUserDto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Failed to update user with id {}. UserDto has errors - {}", userId, bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        UserDetailsDto userDetailsDto = userService.updateUser(userId, updateUserDto);

        LOGGER.info("User with id {} updated successfully. Retrieving updated user", userId);

        return new ResponseEntity<>(userDetailsDto, OK);
    }

    /**
     * Delete user
     *
     * @param userId the user id
     * @return the response entity
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
        LOGGER.info("Request to delete user with id {}", userId);

        userService.deleteUser(userId);

        LOGGER.info("User with id {} deleted successfully", userId);

        return new ResponseEntity<>(OK);
    }
}
