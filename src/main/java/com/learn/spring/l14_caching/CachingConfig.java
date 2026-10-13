package com.learn.spring.l14_caching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableCaching
public class CachingConfig {

    /**
     * A simple in-memory cache (a ConcurrentHashMap per cache name, no expiry).
     * In production swap this bean for Caffeine (expiry, size limits) or Redis (shared across
     * instances). The @Cacheable code does not change. That is the point of the abstraction.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products", "searches");
    }
}
