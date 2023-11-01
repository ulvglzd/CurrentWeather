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
        StringBuilder errorMessages = new StringBuilder();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            errorMessages.append(constraintViolation.getMessage()).append("\n");
        });

        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errorMessages.toString().trim())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(errorObject);
    }

    @ExceptionHandler(NoSuchCityException.class)
    public ResponseEntity<ErrorObject> handleNoSuchCityException(NoSuchCityException ex) {
        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMessages.append(fieldError.getDefaultMessage()).append("\n");
        });

        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errorMessages.toString())
                .timestamp(LocalDateTime.now())
                .build();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(errorMessages.toString());
        errorObject.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(errorObject);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex) {
        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorObject);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorObject);
    }

    @ExceptionHandler(UnableToUploadFileException.class)
    public ResponseEntity<ErrorObject> handleUnableToUploadFileException(UnableToUploadFileException ex) {
        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorObject);
    }

    @ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<ErrorObject> handleUnsupportedFileFormatException(UnsupportedFileFormatException ex) {

        ErrorObject errorObject = new ErrorObject().builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObject);
    }




}
