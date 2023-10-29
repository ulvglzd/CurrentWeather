package com.ulviglzd.weatherapi.contoller;

import com.ulviglzd.weatherapi.dto.weatherDto.WeatherDTO;
import com.ulviglzd.weatherapi.helpers.validators.ValidCityName;
import com.ulviglzd.weatherapi.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/current-weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeatherData(@PathVariable("city") @ValidCityName String cityName) {

        return ResponseEntity.ok(weatherService.getCurrentWeather(cityName));
    }





}
