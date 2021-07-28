package academy.mindswap.rentacarapi.service;

import academy.mindswap.rentacarapi.command.user.UserAuthenticatedDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;
import academy.mindswap.rentacarapi.converter.UserEntityToUserDtoConverter;
import academy.mindswap.rentacarapi.exception.AuthenticationFailureException;
import academy.mindswap.rentacarapi.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private UserServiceImp userService;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetailsDto login(UserAuthenticatedDto userAuthenticatedDto) {
        UserEntity user = userService.findUserByEmail(userAuthenticatedDto.getEmail());

        if (passwordEncoder.matches(userAuthenticatedDto.getPassword(), user.getPassword())) {
            return UserEntityToUserDtoConverter.convert(user);
        }

        throw new AuthenticationFailureException();
    }

    @Autowired
    public void setUserService(UserServiceImp userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
