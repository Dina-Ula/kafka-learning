package com.lbg.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class RateLimiterSetup {

    public static final int BUCKETS_PER_GROUP = 100;

    private static final List<String> APP_GROUPS = List.of("a", "b", "c");

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {

        final RateLimiterConfig bucketConfig = RateLimiterConfig.custom()
                .limitForPeriod(10)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ZERO)
                .build();

        final RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(bucketConfig);

        /*for (String group : APP_GROUPS) {
            for (int i = 0; i < BUCKETS_PER_GROUP; i++) {
                String name = "%s-bucket-%d".formatted(group, i);
                rateLimiterRegistry.rateLimiter(name);
            }
        }*/

        return rateLimiterRegistry;
    }
}
