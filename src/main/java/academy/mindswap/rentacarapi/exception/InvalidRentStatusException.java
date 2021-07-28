package academy.mindswap.rentacarapi.exception;

/**
 * A {@link RentacarApiException} for when the current rent status is not valid for the requested action
 */
public class InvalidRentStatusException extends RentacarApiException {

    public InvalidRentStatusException(String message) {
        super(message);
    }
}
