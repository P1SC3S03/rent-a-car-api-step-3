package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.user.CreateOrUpdateUserDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;

import java.util.List;

/**
 * Common interface for user services, provides methods to manage users
 */
public interface UserService {

    /**
     * Create new user
     * @param userRegistrationDto User data to create
     * @return the user created
     */
    UserDetailsDto createUser(CreateOrUpdateUserDto userRegistrationDto);

    /**
     * Get user by id
     * @param userId user we want to find
     * @return {@link UserDetailsDto}
     */
    UserDetailsDto getUserById(long userId);

    /**
     * Get a list with all users
     * @return a list with {@link UserDetailsDto}
     */
    List<UserDetailsDto> getUsersList();

    /**
     * Delete user by userID
     * @param userId user we want to delete
     */
    void deleteUser(long userId);

    /**
     * Update user details
     * @param userId user to be updated
     * @param updateUserDto data to update
     * @return user details updated
     */
    UserDetailsDto updateUser(long userId, CreateOrUpdateUserDto updateUserDto);
}
