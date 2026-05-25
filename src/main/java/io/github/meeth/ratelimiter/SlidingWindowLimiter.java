package main.java.io.github.meeth.ratelimiter;

public class SlidingWindowLimiter implements RateLimiter {
    RateLimiterConfig config;

    public SlidingWindowLimiter(RateLimiterConfig config) {
        this.config=config;
    }

    @Override
    public boolean consume(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public boolean consume(String key, int permits) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
