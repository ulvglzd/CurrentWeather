package com.ulviglzd.weatherapi.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorObject> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorObject errorObject = new ErrorObject();
        StringBuilder errorMessages = new StringBuilder();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            errorMessages.append(constraintViolation.getMessage()).append("\n");
        });

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(errorMessages.toString().trim());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(errorObject);
    }

    @ExceptionHandler(NoSuchCityException.class)
    public ResponseEntity<ErrorObject> handleNoSuchCityException(NoSuchCityException ex) {
        ErrorObject errorObject = new ErrorObject();

        //extracting the error message from the exception and setting it to the error object
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }


}
