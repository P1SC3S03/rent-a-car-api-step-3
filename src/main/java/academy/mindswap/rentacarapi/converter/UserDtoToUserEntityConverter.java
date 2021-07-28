package academy.mindswap.rentacarapi.converter;

import academy.mindswap.rentacarapi.command.user.CreateOrUpdateUserDto;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;

/**
 * Converter helper for user converting between DTO and entity
 */
public class UserDtoToUserEntityConverter {

    /**
     * Convert from {@link CreateOrUpdateUserDto} to {@link UserEntity}
     * @param createOrUpdateUserDto
     * @return {@link UserEntity}
     */
    public static UserEntity convert(CreateOrUpdateUserDto createOrUpdateUserDto) {
        return UserEntity.builder()
                .firstName(createOrUpdateUserDto.getFirstName())
                .lastName(createOrUpdateUserDto.getLastName())
                .email(createOrUpdateUserDto.getEmail())
                .licenseId(createOrUpdateUserDto.getLicenseId())
                .password(createOrUpdateUserDto.getPassword())
                .build();
    }
}
