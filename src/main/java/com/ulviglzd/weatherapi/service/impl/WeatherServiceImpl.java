package com.ulviglzd.weatherapi.service.impl;

import com.ulviglzd.weatherapi.dto.weatherDto.WeatherDTO;
import com.ulviglzd.weatherapi.dto.weatherDto.WeatherResponse;
import com.ulviglzd.weatherapi.entity.weather.WeatherEntity;
import com.ulviglzd.weatherapi.exceptions.NoSuchCityException;
import com.ulviglzd.weatherapi.exceptions.UserNameAlreadyExistsException;
import com.ulviglzd.weatherapi.externalApi.WeatherStackApiCall;
import com.ulviglzd.weatherapi.helpers.formatters.DateAndTimeFormatter;
import com.ulviglzd.weatherapi.repository.UserRepository;
import com.ulviglzd.weatherapi.repository.WeatherRepository;
import com.ulviglzd.weatherapi.service.WeatherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    private final MailSendingService mailSendingService;
    private final UserRepository userRepository;



    public WeatherServiceImpl(WeatherStackApiCall apiCall,
                              WeatherRepository weatherRepository, MailSendingService mailSendingService, UserRepository userRepository) {
        this.apiCall = apiCall;
        this.weatherRepository = weatherRepository;
        this.mailSendingService = mailSendingService;
        this.userRepository = userRepository;
    }


    @Override
    public WeatherDTO getCurrentWeather(String cityName) {

        //getting weather data from database
        Optional<WeatherEntity> weatherEntity = weatherRepository.findByRequestedCityName(cityName);
        WeatherDTO weatherDTO = null;

        //checking if weather data is present in database and if it is expired
        if (weatherEntity.isPresent() && !isWeatherDataExpired(weatherEntity.get())) {
            log.info("Weather data found in database for city: " + cityName);
            weatherDTO = convertWeatherEntityToWeatherDTO(weatherEntity.get());
        }
        //if weather data is present in database but expired, deleting it and getting new data from api
        else if (weatherEntity.isPresent() && isWeatherDataExpired(weatherEntity.get())) {
            log.info("Weather data expired in database for city: " + cityName);
            weatherRepository.delete(weatherEntity.get());
            WeatherEntity updatedWeatherDataFromApi = weatherRepository.save(generateWeatherEntity(cityName));
            weatherDTO = convertWeatherEntityToWeatherDTO(updatedWeatherDataFromApi);
        }
        //if weather data is not present in database, getting new data from api
        else  {
            log.info("Weather data not found in database for city: " + cityName);
            WeatherEntity updatedWeatherDataFromApi = weatherRepository.save(generateWeatherEntity(cityName));
            weatherDTO = convertWeatherEntityToWeatherDTO(updatedWeatherDataFromApi);
        }

        //Sending mail to user (logging for mail sending is in MailSendingService)
        mailSendingService.sendMail(getUserEmail(), "Weather info for " + cityName,
                convertWeatherDTOtoMailText(weatherDTO));

        return weatherDTO;

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

    //Converting WeatherEntity to WeatherDTO using modelMapper
    private WeatherDTO convertWeatherEntityToWeatherDTO(WeatherEntity weatherEntity) {
        return modelMapper.map(weatherEntity, WeatherDTO.class);
    }

    //Getting logged-in user email from security context
    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //checking if user is authenticated
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            String username = principal.getUsername();
            var user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return user.getEmail();
        }
        return "Authentication failed";
    }

    //Converting WeatherDTO to mail text
    private String convertWeatherDTOtoMailText(WeatherDTO weatherDTO) {
        if (weatherDTO != null) {
            return "City: " + weatherDTO.getCityName() + "\n" +
                    "Country: " + weatherDTO.getCountry() + "\n" +
                    "Temperature: " + weatherDTO.getTemperature() + "\n" +
                    "Local time: " + dateAndTimeFormatter.print(weatherDTO.getUpdateTime(), Locale.getDefault());
        }
        return "No weather data found";
    }


}
