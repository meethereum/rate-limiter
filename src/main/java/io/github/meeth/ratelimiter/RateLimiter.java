package main.java.io.github.meeth.ratelimiter;

public interface RateLimiter {
    boolean consume(String key);

    boolean consume(String key, int permits);

    void reset(String key);
}
