package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.repository.WeatherRepository;
import com.ulviglzd.weatherapi.service.DataCleaningService;
import org.springframework.stereotype.Service;

@Service
public class DataCleaningServiceImpl implements DataCleaningService {
    private final WeatherRepository weatherRepository;

    public DataCleaningServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void eraseOldData() {
        weatherRepository.deleteAll();
    }
}
