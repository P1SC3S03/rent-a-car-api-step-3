package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for when the car already exists
 */
public class CarAlreadyExistsException extends RentacarApiException {

    public CarAlreadyExistsException() {
        super(ErrorMessages.CAR_ALREADY_EXISTS);
    }
}
