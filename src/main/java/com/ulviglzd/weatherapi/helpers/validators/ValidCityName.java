package com.ulviglzd.weatherapi.helpers.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityNameValidator.class)
@Documented
public @interface ValidCityName {
    String message() default "Invalid city name: Provide valid city name please!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
