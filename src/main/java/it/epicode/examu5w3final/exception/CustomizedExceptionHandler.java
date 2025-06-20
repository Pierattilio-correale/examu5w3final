package it.epicode.examu5w3final.exception;

import it.epicode.examu5w3final.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//
import java.time.LocalDateTime;
@RestControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError giaEsistenteExceptionHandler(AlreadyExistException e) {

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError NotFoundExceptionHandler(NotFoundException e) {

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError ValidationExceptionHandler(ValidationException e) {

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }
    @ExceptionHandler(UnauthoraizedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError UnauthoraizedExceptionHandler(UnauthoraizedException e) {

        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;

    }
}