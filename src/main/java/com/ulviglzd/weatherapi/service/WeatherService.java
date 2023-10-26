package com.ulviglzd.weatherapi.service;

import com.ulviglzd.weatherapi.dto.WeatherDTO;

public interface WeatherService {

    WeatherDTO getCurrentWeather(String cityName);
}
