package academy.mindswap.rentacarapi.converter;

import academy.mindswap.rentacarapi.command.user.CreateOrUpdateUserDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;

/**
 * Converter helper for user converting between entity and DTO
 */
public class UserEntityToUserDtoConverter {

    /**
     * Convert from {@link UserEntity} to {@link CreateOrUpdateUserDto}
     * @param userEntity
     * @return {@link UserDetailsDto}
     */
    public static UserDetailsDto convert(UserEntity userEntity) {
        return UserDetailsDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .licenseId(userEntity.getLicenseId())
                .build();
    }

}
