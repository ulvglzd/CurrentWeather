package com.ulviglzd.weatherapi.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorObject {
    @JsonProperty("code")
    private Integer statusCode;
    @JsonProperty("info")
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp;
}
