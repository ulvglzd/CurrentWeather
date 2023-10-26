package com.ulviglzd.weatherapi.externalApi;

import com.ulviglzd.weatherapi.dto.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherStack", url = "${weather.api.base-url}")
public interface WeatherStackApiCall {

    @GetMapping("/current")
    WeatherResponse getCurrentWeatherResponse(@RequestParam("access_key") String accessKey, @RequestParam("query") String query);
}
