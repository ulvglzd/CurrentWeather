package com.ulviglzd.weatherapi.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorObject errorObject = new ErrorObject();
        List<String> errorMessages = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getDefaultMessage());
        });

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(errorMessages.toString());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(errorObject);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex) {
        ErrorObject errorObject = new ErrorObject();

        //extracting the error message from the exception and setting it to the error object
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorObject);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorObject errorObject = new ErrorObject();

        //extracting the error message from the exception and setting it to the error object
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorObject);
    }

    @ExceptionHandler(UnableToUploadFileException.class)
    public ResponseEntity<ErrorObject> handleUnableToUploadFileException(UnableToUploadFileException ex) {
        ErrorObject errorObject = new ErrorObject();

        //extracting the error message from the exception and setting it to the error object
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorObject);
    }




}
