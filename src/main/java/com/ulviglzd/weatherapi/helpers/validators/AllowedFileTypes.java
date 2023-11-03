package com.ulviglzd.weatherapi.helpers.validators;

import java.util.Arrays;

public class AllowedFileTypes {
    public static final String[] IMAGE_TYPES = {"image/png", "image/jpg", "image/jpeg"};

    public static boolean isImageAllowed(String contentType) {
        return Arrays.stream(IMAGE_TYPES)
                .toList()
                .contains(contentType);
    }
}
