package com.ulviglzd.weatherapi.scheduler;

import com.ulviglzd.weatherapi.service.impl.DataCleaningServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataCleaningScheduler {
    private final DataCleaningServiceImpl dataCleaningServiceImpl;

    public DataCleaningScheduler(DataCleaningServiceImpl dataCleaningServiceImpl) {
        this.dataCleaningServiceImpl = dataCleaningServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataCleaningScheduler.class);


    @Scheduled(cron = "0 0 0 1 * ?") // Schedule is set to run every 1st day of the month at midnight
    public void scheduleDataCleanup() {
        logger.info("Data cleanup task started.");
        try {
            dataCleaningServiceImpl.eraseOldData();
            logger.info("Data cleanup task completed successfully.");
        } catch (Exception e) {
            logger.error("Data cleanup task failed: " + e.getMessage(), e);
        }    }
}