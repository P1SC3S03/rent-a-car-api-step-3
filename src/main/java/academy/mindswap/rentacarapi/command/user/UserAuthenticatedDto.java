package academy.mindswap.rentacarapi.command.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO for user creation request
 */
@Data
@Builder
public class UserAuthenticatedDto {

    @NotBlank(message = "Password can not be blank")
    private String password;

    @Email(message = "Email must be valid")
    private String email;

}
