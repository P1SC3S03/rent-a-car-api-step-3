package academy.mindswap.rentacarapi.command.rent;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO to retrieve rent details
 */
@Data
@Builder
public class RentDetailsDto {

    private long rentId;
    private long carId;
    private long userId;
    private Date expectedBeginDate;
    private Date beginDate;
    private Date expectedEndDate;
    private Date endDate;
    private BigDecimal expectedPrice;
    private BigDecimal finalPrice;
}
