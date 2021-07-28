package academy.mindswap.rentacarapi.command.user;

import lombok.Builder;
import lombok.Data;

/**
 * DTO to retrieve user details
 */
@Data
@Builder
public class UserDetailsDto {

    private long userId;
    private String firstName;
    private String lastName;
    private String licenseId;
    private String email;
}
