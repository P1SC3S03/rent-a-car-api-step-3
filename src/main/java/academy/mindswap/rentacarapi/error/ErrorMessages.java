package academy.mindswap.rentacarapi.error;

/**
 * Class with constants for error messages
 */
public class ErrorMessages {

    /**
     * Private constructor to avoid unnecessary instantiation
     */
    private ErrorMessages() { }

    public static final String USER_NOT_FOUND = "Can't find the user with the provided id";
    public static final String USER_ALREADY_EXISTS = "A user with the provided email already exists";

    public static final String CAR_NOT_FOUND = "Can't find the car with the provided id";
    public static final String CAR_ALREADY_EXISTS = "A car with the provided plate already exists";
    public static final String CAR_NOT_AVAILABLE = "The required car is not available at the moment";

    public static final String RENT_NOT_FOUND = "Can't find the rent with the provided id";
    public static final String CAR_ALREADY_DELIVERED = "The car has already been delivered to the customer";
    public static final String CAR_EXPECTED_TO_BE_UNAVAILABLE = "The car will be unavailable in the provided dates";
    public static final String CAN_NOT_RETURN_CAR = "The current rent status doesn't allow you to return the car";
    public static final String CAN_NOT_DELETE_CAR_ALREADY_DELIVERED = "The rent cannot be deleted because the car was already delivered";

    public static final String DATABASE_COMMUNICATION_ERROR = "Something went wrong with our database connection. Please try again later.";
}
