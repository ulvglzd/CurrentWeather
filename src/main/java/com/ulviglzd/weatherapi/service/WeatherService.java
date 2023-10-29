package com.ulviglzd.weatherapi.service;

import com.ulviglzd.weatherapi.dto.weatherDto.WeatherDTO;

public interface WeatherService {

    WeatherDTO getCurrentWeather(String cityName);
}
