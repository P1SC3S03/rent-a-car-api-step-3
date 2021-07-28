package academy.mindswap.rentacarapi.command.car;

import academy.mindswap.rentacarapi.enumerator.CarBrands;
import academy.mindswap.rentacarapi.enumerator.CarSegment;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * DTO for car creation or update requests
 */
@Data
@Builder
public class CreateOrUpdateCarDto {

    @NotNull
    private CarBrands brand;

    @NotBlank(message = "Must have model description")
    private String modelDescription;

    @NotNull
    private CarSegment carSegment;

    @PastOrPresent
    private Date dateOfPurchase;

    @NotBlank(message = "Must have plate")
    private String plate;

    @Override
    public String toString() {
        return "CreateOrUpdateCarDto{" +
                "brand=" + brand +
                ", modelDescription='" + modelDescription + '\'' +
                ", carSegment=" + carSegment +
                ", dateOfPurchase=" + dateOfPurchase +
                ", plate='" + plate + '\'' +
                '}';
    }
}
