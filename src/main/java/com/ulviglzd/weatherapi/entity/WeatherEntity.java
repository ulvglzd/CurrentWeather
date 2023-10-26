package com.ulviglzd.weatherapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather_table")
@Builder
public class WeatherEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String requestedCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updateTime;
    private LocalDateTime responseLocalTime;




}
