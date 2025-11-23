package com.lbg.service;

import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.lbg.config.RateLimiterSetup.BUCKETS_PER_GROUP;

@Service
public class ExternalService {
    private final RateLimiterRegistry rateLimiterRegistry;

    public ExternalService(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    public String post(String appGroup, String accId) throws RequestNotPermitted {

        final String rateLimiterName = "%s-bucket-%d".formatted(appGroup, Integer.parseInt(accId) % BUCKETS_PER_GROUP);

        final RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(rateLimiterName);

        final Supplier<String> supplier = () -> "ok: " + appGroup + ":" + accId + ":" + rateLimiterName;

        try {
            return Decorators.ofSupplier(supplier)
                    .withRateLimiter(rateLimiter)
                    .get();
        } catch (RequestNotPermitted ex) {
            return "nok: " + appGroup + ":" + accId + ":" + rateLimiterName;
        }
    }
}
