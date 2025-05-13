package vab.com.vn.emailnhacno.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    // Lưu văn bản tĩnh vào cache
    @Cacheable(value = "textCache", key = "'staticText'", unless = "#result == null")
    public String getStaticText() {
        logger.info("Calling getStaticText() method to store data in cache.");
        return "BEST YONE VN";
    }
}