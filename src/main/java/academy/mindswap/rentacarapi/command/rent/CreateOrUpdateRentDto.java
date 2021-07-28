package academy.mindswap.rentacarapi.command.rent;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO for rent creation request
 */
@Data
@Builder
public class CreateOrUpdateRentDto {

    @NotNull(message = "Must provide an userId")
    private long userId;

    @NotNull(message = "Must provide a carId")
    private long carId;

    @NotNull(message = "Must have an expected beginDate")
    @FutureOrPresent
    private Date expectedBeginDate;

    @NotNull(message = "Must have an expected endDate")
    @Future
    private Date expectedEndDate;
}
