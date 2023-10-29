package com.ulviglzd.weatherapi.dto.weatherDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {
    private String name;
    private String country;
    private String region;
    private String lat;
    private String lon;
    @JsonProperty("timezone_id")
    private String timezoneId;
    private String localtime;
    @JsonProperty("localtime_epoch")
    private int localtimeEpoch;
    @JsonProperty("utc_offset")

    private String utcOffset;



}
