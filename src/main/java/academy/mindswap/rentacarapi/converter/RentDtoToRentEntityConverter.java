package academy.mindswap.rentacarapi.converter;

import academy.mindswap.rentacarapi.command.rent.CreateOrUpdateRentDto;
import academy.mindswap.rentacarapi.persistence.entity.RentEntity;

/**
 * Converter helper for rent converting between DTO and entity
 */
public class RentDtoToRentEntityConverter {

    /**
     * Convert from {@link CreateOrUpdateRentDto} to {@link RentEntity}
     * @param createOrUpdateRentDto
     * @return {@link RentEntity}
     */
    public static RentEntity convert(CreateOrUpdateRentDto createOrUpdateRentDto) {
        return RentEntity.builder()
                .expectedBeginDate(createOrUpdateRentDto.getExpectedBeginDate())
                .expectedEndDate(createOrUpdateRentDto.getExpectedEndDate())
                .build();
    }
}
