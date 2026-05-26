package main.java.io.github.meeth.ratelimiter;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowLimiter implements RateLimiter {
    RateLimiterConfig config;
    private final Deque<Long> requestTimestamps = new ArrayDeque<>();

    public SlidingWindowLimiter(RateLimiterConfig config) {
        this.config = config;
    }

    @Override
    public boolean consume() {
        long now = System.currentTimeMillis();

        while (!requestTimestamps.isEmpty() && now - requestTimestamps.peekFirst() >= config.rate() * 1000) {
            requestTimestamps.pollFirst();
        }

        if (requestTimestamps.size() < config.capacity()) {
            requestTimestamps.addLast(now);
            return true;
        }
        return false;
    }

    @Override
    public boolean consume(int permits) {
        long now = System.currentTimeMillis();

        while (!requestTimestamps.isEmpty() && now - requestTimestamps.peekFirst() >= config.rate() * 1000) {
            requestTimestamps.pollFirst();
        }

        if (requestTimestamps.size() + permits <= config.capacity()) {
            for (int i = 0; i < permits; i++) {
                requestTimestamps.addLast(now);
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        requestTimestamps.clear();
    }

}
