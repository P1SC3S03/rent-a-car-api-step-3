package academy.mindswap.rentacarapi.error;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Error model
 */
@Data
@Builder
public class Error {

    private Date timestamp;
    private String message;
    private String method;
    private String exception;
    private String path;
}
