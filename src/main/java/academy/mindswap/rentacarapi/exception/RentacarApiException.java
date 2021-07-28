package academy.mindswap.rentacarapi.exception;

/**
 * Global rentacar exception
 */
public class RentacarApiException extends RuntimeException {
    public RentacarApiException() {
    }

    public RentacarApiException(String message) {
        super(message);
    }

    public RentacarApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
