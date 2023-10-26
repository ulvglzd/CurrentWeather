package com.ulviglzd.weatherapi.helpers.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateAndTimeFormatter implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String text, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(text, formatter);
    }

        @Override
        public String print(LocalDateTime object, Locale locale) {
            return object.getHour() + object.getDayOfMonth() + " " + object.getMonth() + " " + object.getYear();
        }

}

