package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for car not found
 */
public class CarNotFoundException extends RentacarApiException {

    public CarNotFoundException() {
        super(ErrorMessages.CAR_NOT_FOUND);
    }
}
