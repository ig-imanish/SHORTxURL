package com.urlshortner.shortxurl.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.urlshortner.shortxurl.dao.UrlRepo;

@Component
@EnableScheduling
public class UrlCleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(UrlCleanupScheduler.class);
    
    @Autowired
    private UrlRepo urlRepository;
    
    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredUrls() {
        logger.info("Starting URL cleanup process");
        
        LocalDateTime expirationThreshold = LocalDateTime.now().minus(28, ChronoUnit.DAYS);
        
        int deletedCount = urlRepository.deleteByCreatedAtBefore(expirationThreshold);
        
        logger.info("URL cleanup completed: {} expired URLs deleted", deletedCount);
    }
}