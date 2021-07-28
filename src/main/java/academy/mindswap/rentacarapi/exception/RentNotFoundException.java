package academy.mindswap.rentacarapi.exception;

import academy.mindswap.rentacarapi.error.ErrorMessages;

/**
 * A {@link RentacarApiException} for rent not found
 */
public class RentNotFoundException extends RentacarApiException {

    public RentNotFoundException(){
        super(ErrorMessages.RENT_NOT_FOUND);
    }
}
