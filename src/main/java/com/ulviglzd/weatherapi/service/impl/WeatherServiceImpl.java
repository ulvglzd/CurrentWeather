package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.dto.WeatherDTO;
import com.ulviglzd.weatherapi.dto.WeatherResponse;
import com.ulviglzd.weatherapi.entity.WeatherEntity;
import com.ulviglzd.weatherapi.exceptions.NoSuchCityException;
import com.ulviglzd.weatherapi.externalApi.WeatherStackApiCall;
import com.ulviglzd.weatherapi.helpers.formatters.DateAndTimeFormatter;
import com.ulviglzd.weatherapi.repository.WeatherRepository;
import com.ulviglzd.weatherapi.service.WeatherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private ModelMapper modelMapper;
    private final WeatherStackApiCall apiCall;
    private final WeatherRepository weatherRepository;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Autowired
    private DateAndTimeFormatter dateAndTimeFormatter;

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);



    public WeatherServiceImpl(WeatherStackApiCall apiCall,
                              WeatherRepository weatherRepository) {
        this.apiCall = apiCall;
        this.weatherRepository = weatherRepository;
    }


    @Override
    public WeatherDTO getCurrentWeather(String cityName) {

        //getting weather data from database
        Optional<WeatherEntity> weatherEntity = weatherRepository.findByRequestedCityName(cityName);

        if (weatherEntity.isPresent() && !isWeatherDataExpired(weatherEntity.get())) {
            log.info("Weather data found in database for city: " + cityName);
            return convertWeatherEntityToWeatherDTO(weatherEntity.get());
        }
        else if (weatherEntity.isPresent() && isWeatherDataExpired(weatherEntity.get())) {
            log.info("Weather data expired in database for city: " + cityName);
            weatherRepository.delete(weatherEntity.get());
            WeatherEntity updatedWeatherDataFromApi = weatherRepository.save(generateWeatherEntity(cityName));
            return convertWeatherEntityToWeatherDTO(updatedWeatherDataFromApi);
        }
        else  {
            log.info("Weather data not found in database for city: " + cityName);
            WeatherEntity updatedWeatherDataFromApi = weatherRepository.save(generateWeatherEntity(cityName));
            return convertWeatherEntityToWeatherDTO(updatedWeatherDataFromApi);
        }

    }

    private WeatherEntity generateWeatherEntity(String cityName) {

        //Sending request to weather api via WeatherStackApiCall
        WeatherResponse weatherResponse = apiCall.getCurrentWeatherResponse(weatherApiKey, cityName);
        if (weatherResponse.getError() != null) {
            throw new NoSuchCityException("No such city: " + cityName + " found in database");
        }

        //Building weather entity from response
        WeatherEntity weatherEntity = WeatherEntity.builder()
                    .requestedCityName(cityName)
                    .cityName(weatherResponse.getLocation().getName())
                    .country(weatherResponse.getLocation().getCountry())
                    .temperature(weatherResponse.getCurrent().getTemperature())
                    .updateTime(LocalDateTime.now())
                    .responseLocalTime(dateAndTimeFormatter
                            .parse(weatherResponse.getLocation().getLocaltime(), Locale.getDefault()))
                    .build();
        return weatherEntity;
    }

    //checks if weather data in database is expired
    private boolean isWeatherDataExpired(WeatherEntity weatherEntity) {
        return weatherEntity.getUpdateTime().isBefore(LocalDateTime.now().minusMinutes(40));
    }

    private WeatherDTO convertWeatherEntityToWeatherDTO(WeatherEntity weatherEntity) {
        return modelMapper.map(weatherEntity, WeatherDTO.class);
    }


}
