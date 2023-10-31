package com.ulviglzd.weatherapi.service;

import org.springframework.stereotype.Service;

@Service
public interface DataCleaningService {
    void eraseOldData();
}
