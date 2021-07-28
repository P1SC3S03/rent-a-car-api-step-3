package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
* A {@link RentacarApiException} for database connections errors
*/
public class DatabaseCommunicationException extends RentacarApiException {

    public DatabaseCommunicationException(Throwable cause) {
        super(ErrorMessages.DATABASE_COMMUNICATION_ERROR, cause);
    }
}
