package academy.mindswap.rentacarapi.exception;

public class AuthenticationFailureException extends RentacarApiException {

    public AuthenticationFailureException() {
        super("Bad credentials! :( ");
    }
}
