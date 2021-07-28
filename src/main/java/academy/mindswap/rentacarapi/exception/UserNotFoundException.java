package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for user not found
 */
public class UserNotFoundException extends RentacarApiException {

    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND);
    }
}
