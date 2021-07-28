package academy.mindswap.rentacarapi.converter;

import academy.mindswap.rentacarapi.command.car.CarDetailsDto;
import academy.mindswap.rentacarapi.persistence.entity.CarEntity;

/**
 * Converter helper for car converting between entity and DTO
 */
public class CarEntityToCarDtoConverter {

    /**
     * Convert from {@link CarEntity} to {@link CarDetailsDto}
     * @param carEntity
     * @return {@link CarDetailsDto}
     */
    public static CarDetailsDto convert(CarEntity carEntity) {
        return CarDetailsDto.builder()
                .carId(carEntity.getCarId())
                .brand(carEntity.getBrand())
                .modelDescription(carEntity.getModelDescription())
                .dateOfPurchase(carEntity.getDateOfPurchase())
                .carSegment(carEntity.getCarSegment())
                .plate(carEntity.getPlate())
                .available(carEntity.isAvailable())
                .dailyPrice(carEntity.getCarSegment().getDailyPrice())
                .build();
    }

}
