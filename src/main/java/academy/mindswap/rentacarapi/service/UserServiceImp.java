package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.user.CreateOrUpdateUserDto;
import academy.mindswap.rentacarapi.command.user.UserAuthenticatedDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;
import academy.mindswap.rentacarapi.converter.UserDtoToUserEntityConverter;
import academy.mindswap.rentacarapi.converter.UserEntityToUserDtoConverter;
import academy.mindswap.rentacarapi.exception.AuthenticationFailureException;
import academy.mindswap.rentacarapi.exception.DatabaseCommunicationException;
import academy.mindswap.rentacarapi.exception.UserAlreadyExistsException;
import academy.mindswap.rentacarapi.exception.UserNotFoundException;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;
import academy.mindswap.rentacarapi.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An {@link UserService} implementation
 */
@Service
public class UserServiceImp implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @see UserService#createUser(CreateOrUpdateUserDto)
     */
    @Override
    public UserDetailsDto createUser(CreateOrUpdateUserDto userRegistrationDto) {

        // Build UserEntity
        UserEntity userEntity = UserDtoToUserEntityConverter.convert(userRegistrationDto);

        //Set Password to Hashed Password
        LOGGER.info("Setting password.");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));


        LOGGER.debug("Persisting new user into database");

        // Persist user into database
        UserEntity createdUser;

        try {
            createdUser = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Duplicated email - {}", userEntity.getEmail(), e);
            throw new UserAlreadyExistsException();
        } catch (Exception e) {
            LOGGER.error("Failed while saving user into database {}", userEntity, e);
            throw new DatabaseCommunicationException(e);
        }

        // Build UserDetailsDto to return to the client
        return UserEntityToUserDtoConverter.convert(createdUser);
    }

    /**
     * @see UserService#getUserById(long)
     */
    @Override
    public UserDetailsDto getUserById(long userId) {
        LOGGER.debug("Getting user with id {} from database", userId);

        // Get user details from database
        UserEntity userEntity = getUserEntityById(userId);

        // Build UserDetailsDto to return to the client
        return UserEntityToUserDtoConverter.convert(userEntity);
    }

    /**
     * @see UserService#getUsersList()
     */
    @Override
    public List<UserDetailsDto> getUsersList() {
        LOGGER.debug("Getting all users from database");

        // Convert list items from UserEntity to UserDetailsDto
        List<UserDetailsDto> usersListResponse = new ArrayList<>();

        try {
            for (UserEntity user : userRepository.findAll()) {
                usersListResponse.add(UserEntityToUserDtoConverter.convert(user));
            }
        } catch (Exception e) {
            LOGGER.error("Failed while getting all users from database", e);
            throw new DatabaseCommunicationException(e);
        }

        return usersListResponse;
    }

    /**
     * @see UserService#deleteUser(long)
     */
    @Override
    public void deleteUser(long userId) {

        // Verify if the user exists
        LOGGER.debug("Verifying if user with id {} exists in database", userId);
        UserEntity user = getUserEntityById(userId);

        // Delete user
        LOGGER.debug("Removing user with id {} from database", userId);

        try {
            userRepository.delete(user);
        } catch (Exception e) {
            LOGGER.error("Failed while deleting user with id {} from database", userId, e);
            throw new DatabaseCommunicationException(e);
        }
    }

    /**
     * @see UserService#updateUser(long, CreateOrUpdateUserDto)
     */
    @Override
    public UserDetailsDto updateUser(long userId, CreateOrUpdateUserDto updateUserDto) {
        LOGGER.debug("Verifying if user with id {} exists in database", userId);

        // Verify if the user exists
        UserEntity userEntity = getUserEntityById(userId);

        // Update data with userDetails received
        userEntity.setFirstName(updateUserDto.getFirstName());
        userEntity.setLastName(updateUserDto.getLastName());
        userEntity.setEmail(updateUserDto.getEmail());
        userEntity.setLicenseId(updateUserDto.getLicenseId());
        userEntity.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));

        // Save changes
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while updating user with id {} in database with new data - {}", userId, userEntity, e);
            throw new DatabaseCommunicationException(e);
        }

        return UserEntityToUserDtoConverter.convert(userEntity);
    }

    protected UserEntity getUserEntityById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("The user with id {} does not exist in the database", userId);
                    return new UserNotFoundException();
                });
    }

/*    public UserDetailsDto findUserByEmailAndPassword(UserAuthenticatedDto userAuthenticatedDto){
        UserEntity user = userRepository.findByEmailAndPassword(userAuthenticatedDto.getEmail()
                , userAuthenticatedDto.)
                .orElseThrow(() -> {
                    LOGGER.error("Authentication failure. Criteria mismatch");
                    return new AuthenticationFailureException();
                });
        return UserEntityToUserDtoConverter.convert(user);

    }*/

    protected UserEntity findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    LOGGER.error("The user with email {} does not exist in the database", email);
                    return new UserNotFoundException();
                });
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
