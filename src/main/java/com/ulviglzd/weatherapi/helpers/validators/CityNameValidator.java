package com.ulviglzd.weatherapi.helpers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CityNameValidator implements ConstraintValidator<ValidCityName, String> {
    @Override
    public void initialize(ValidCityName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String inputString, ConstraintValidatorContext context) {

        String trimmedString = inputString.trim();
        //checking if input is null or empty string
        if (trimmedString == null || trimmedString.isEmpty() || trimmedString.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("City name must not be empty")
                    .addConstraintViolation();
            return false;
        }



        //checking if input contains only alphabetic characters
        if (!trimmedString.matches("^[a-zA-Z]*$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("City name must contain only alphabetic characters")
                    .addConstraintViolation();
            return false;
        }

        return true;

    }
}
