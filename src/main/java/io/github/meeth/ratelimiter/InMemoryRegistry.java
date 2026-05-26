package io.github.meeth.ratelimiter;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRegistry implements RateLimiterRegistry {
    ConcurrentHashMap<String, RateLimiter> registryMap = new ConcurrentHashMap<>();
    RateLimiterConfig config;

    public InMemoryRegistry(RateLimiterConfig config) {
        this.config = config;
    }

    private RateLimiter getOrCreate(String key) {
        return registryMap.computeIfAbsent(key, k -> switch (config.algorithm()) {
            case TOKEN_BUCKET -> new TokenBucketLimiter(config);
            case SLIDING_WINDOW -> new SlidingWindowLimiter(config);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + config.algorithm());
        });
    }

    @Override
    public boolean consume(String key) {
        return getOrCreate(key).consume();
    }

    @Override
    public boolean consume(String key, int permits) {
        return getOrCreate(key).consume(permits);
    }

    @Override
    public void reset(String key) {
        RateLimiter limiter = registryMap.get(key);
        if (limiter != null) {
            limiter.reset();
        }
    }
}