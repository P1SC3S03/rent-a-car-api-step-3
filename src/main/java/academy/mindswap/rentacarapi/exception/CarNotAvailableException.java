package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for when the car is already rented
 */
public class CarNotAvailableException extends RentacarApiException {

    public CarNotAvailableException() {
        super(ErrorMessages.CAR_NOT_AVAILABLE);
    }
}
