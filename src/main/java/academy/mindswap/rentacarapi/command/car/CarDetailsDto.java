package academy.mindswap.rentacarapi.command.car;

import academy.mindswap.rentacarapi.enumerator.CarBrands;
import academy.mindswap.rentacarapi.enumerator.CarSegment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO to retrieve car details
 */
@Data
@Builder
public class CarDetailsDto {

    private long carId;
    private CarBrands brand;
    private String modelDescription;
    private CarSegment carSegment;
    private Date dateOfPurchase;
    private String plate;
    private BigDecimal dailyPrice;
    private boolean available;
}
