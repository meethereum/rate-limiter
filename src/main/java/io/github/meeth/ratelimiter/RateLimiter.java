package io.github.meeth.ratelimiter;

interface RateLimiter {
    boolean consume();
    boolean consume(int permits);
    void reset();
}