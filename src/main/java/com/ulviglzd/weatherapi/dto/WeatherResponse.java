package com.ulviglzd.weatherapi.dto;

import com.ulviglzd.weatherapi.exceptions.ErrorObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class WeatherResponse {

    private Request request;
    private Location location;
    private Current current;

    private boolean success;
    private ErrorObject error;

}
