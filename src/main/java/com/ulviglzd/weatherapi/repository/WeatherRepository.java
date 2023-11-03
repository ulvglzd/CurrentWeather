package com.ulviglzd.weatherapi.repository;

import com.ulviglzd.weatherapi.entity.weather.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface WeatherRepository extends JpaRepository<WeatherEntity, UUID> {

    Optional<WeatherEntity> findByRequestedCityName(String cityName);
}
