package academy.mindswap.rentacarapi.converter;


import academy.mindswap.rentacarapi.command.rent.RentDetailsDto;
import academy.mindswap.rentacarapi.persistence.entity.RentEntity;

/**
 * Converter helper for rent converting between entity and DTO
 */
public class RentEntityToRentDtoConverter {

    /**
     * Convert from {@link RentEntity} to {@link RentDetailsDto}
     * @param rentEntity
     * @return {@link RentDetailsDto}
     */
    public static RentDetailsDto convert(RentEntity rentEntity) {
        return RentDetailsDto.builder()
                .rentId(rentEntity.getRentId())
                .carId(rentEntity.getCarEntity().getCarId())
                .userId(rentEntity.getUserEntity().getUserId())
                .expectedBeginDate(rentEntity.getExpectedBeginDate())
                .beginDate(rentEntity.getBeginDate())
                .expectedEndDate(rentEntity.getExpectedEndDate())
                .endDate(rentEntity.getEndDate())
                .expectedPrice(rentEntity.getExpectedPrice())
                .finalPrice(rentEntity.getFinalPrice())
                .build();
    }

}
