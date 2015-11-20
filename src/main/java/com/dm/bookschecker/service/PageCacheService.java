package com.dm.bookschecker.service;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dm.bookschecker.utils.Constants;

@Service
public class PageCacheService {

    private static final Logger logger = LogManager.getLogger(PageCacheService.class);

    private final Map<String, Long> pages = new HashMap<>();

    public void add(String pagePath) {
        pages.put(pagePath, System.currentTimeMillis());
    }

    public void clearCache() {
        logger.info("Clearing started");
        long lastTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(Constants.CLEAR_PERIOD_HOURS);
        synchronized (PageCacheService.this) {
            Set<String> toRemove = pages.entrySet().stream().filter(e -> e.getValue() < lastTime).map(Map.Entry::getKey)
                    .collect(toSet());
            for (String filePath : toRemove) {
                File file = new File(filePath);
                if (!file.delete()) {
                    logger.warn("File was already removed {}", filePath);
                }
                pages.remove(filePath);
            }
        }
        logger.info("Clearing finished");
    }

}
