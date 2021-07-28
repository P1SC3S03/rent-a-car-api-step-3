package academy.mindswap.rentacarapi.controller;

import academy.mindswap.rentacarapi.exception.*;
import academy.mindswap.rentacarapi.error.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class RentacarExceptionHandler {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            CarNotFoundException.class})
    public ResponseEntity<Error> handlerNotFoundException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            CarAlreadyExistsException.class,
            CarNotAvailableException.class})
    public ResponseEntity<Error> handlerConflictException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {
            AuthenticationFailureException.class})
    public ResponseEntity<Error> handleUnauthorizedException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {
            DatabaseCommunicationException.class,
            Exception.class})
    public ResponseEntity<Error> handlerAnyOtherException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Error> buildErrorResponse(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        Error error = Error.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURI())
                .exception(ex.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(error, httpStatus);
    }
}