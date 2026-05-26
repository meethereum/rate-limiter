package io.github.meeth.ratelimiter;

public interface RateLimiterRegistry {

    boolean consume(String key);

    boolean consume(String key, int permits);

    void reset(String key);

}
