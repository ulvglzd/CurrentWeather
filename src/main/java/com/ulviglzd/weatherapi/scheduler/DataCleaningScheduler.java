package com.ulviglzd.weatherapi.scheduler;

import com.ulviglzd.weatherapi.service.DataCleaningService;
import com.ulviglzd.weatherapi.service.impl.DataCleaningServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataCleaningScheduler {
    private final DataCleaningService dataCleaningService;

    public DataCleaningScheduler(DataCleaningService dataCleaningService) {
        this.dataCleaningService = dataCleaningService;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataCleaningScheduler.class);


    @Scheduled(cron = "0 0 0 1 * ?") // Schedule is set to run every 1st day of the month at midnight
    public void scheduleDataCleanup() {
        logger.info("Data cleaning started.");
        try {
            dataCleaningService.eraseOldData();
            logger.info("Data cleaning completed successfully.");
        } catch (Exception e) {
            logger.error("Data cleaning failed: " + e.getMessage(), e);
        }    }
}
