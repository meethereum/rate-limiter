package io.github.meeth.ratelimiter;

public class TokenBucketLimiter implements RateLimiter {
    private int currentTokens;
    private long lastRefillTime;

    RateLimiterConfig config;

    public TokenBucketLimiter(RateLimiterConfig config) {
        this.config = config;
        this.currentTokens = config.capacity();
        this.lastRefillTime = System.currentTimeMillis();
    }



    @Override
    public boolean consume() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;
        int tokensToAdd = (int) (elapsed * config.rate() / 1000);

        if (tokensToAdd > 0) {
            currentTokens = Math.min(currentTokens + tokensToAdd, config.capacity());
            lastRefillTime = now;
        }

        if (currentTokens > 0) {
            currentTokens--;
            return true;
        }
        return false;
    }

    @Override
    public boolean consume(int permits) {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;
        int tokensToAdd = (int) (elapsed * config.rate() / 1000);

        if (tokensToAdd > 0) {
            currentTokens = Math.min(currentTokens + tokensToAdd, config.capacity());
            lastRefillTime = now;
        }

        if (currentTokens >= permits) {
            currentTokens -= permits;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        currentTokens = config.capacity();
        lastRefillTime = System.currentTimeMillis();
    }

}
