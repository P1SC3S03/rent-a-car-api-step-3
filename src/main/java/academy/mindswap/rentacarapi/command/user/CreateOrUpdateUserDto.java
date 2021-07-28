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
public class CreateOrUpdateUserDto {

    @NotBlank(message = "Must have first name")
    private String firstName;

    @NotBlank(message = "Must have last name")
    private String lastName;

    @NotBlank(message = "Must have license ID")
    private String licenseId;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Must have a password")
    private String password;

}
