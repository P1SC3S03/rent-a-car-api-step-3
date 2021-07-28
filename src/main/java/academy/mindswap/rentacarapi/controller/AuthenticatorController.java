package academy.mindswap.rentacarapi.controller;

import academy.mindswap.rentacarapi.command.user.UserAuthenticatedDto;
import academy.mindswap.rentacarapi.command.user.UserDetailsDto;
import academy.mindswap.rentacarapi.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
public class AuthenticatorController {

    private static Logger LOGGER = LogManager.getLogger(AuthenticatorController.class);
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@Valid @RequestBody UserAuthenticatedDto userAuthenticatedDto,
                                     BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            LOGGER.error("Invalid" , bindingResult.getFieldErrors());
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        UserDetailsDto authenticatedUser = authenticationService.login(userAuthenticatedDto);

        if(Objects.isNull(authenticatedUser)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
