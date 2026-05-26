package main.java.io.github.meeth.ratelimiter;

public class TokenBucketLimiter implements RateLimiter {
    RateLimiterConfig config;
    public TokenBucketLimiter(RateLimiterConfig config) {
        this.config = config;
    }

    @Override
    public boolean consume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean consume(int permits) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
