package com.ulviglzd.weatherapi.dto.weatherDto;

import com.ulviglzd.weatherapi.exceptions.ErrorObject;
import lombok.Data;

@Data
public class WeatherResponse {

    private Request request;
    private Location location;
    private Current current;

    private boolean success;
    private ErrorObject error;

}
