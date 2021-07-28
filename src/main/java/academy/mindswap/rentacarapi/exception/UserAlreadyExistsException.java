package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for when user already exists
 */
public class UserAlreadyExistsException extends RentacarApiException {

    public UserAlreadyExistsException() {
        super(ErrorMessages.USER_ALREADY_EXISTS);
    }
}
