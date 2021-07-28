package academy.mindswap.rentacarapi.service;


import academy.mindswap.rentacarapi.command.user.UserAuthenticatedDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;

public interface AuthenticationService {

     UserDetailsDto login(UserAuthenticatedDto userAuthenticatedDto);
}
