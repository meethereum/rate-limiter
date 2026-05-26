package io.github.meeth.ratelimiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenBucketLimiterTest {

    private InMemoryRegistry registry;

    @BeforeEach
    void setUp() {
        RateLimiterConfig config = new RateLimiterConfig(Algorithm.TOKEN_BUCKET, 5, 1);
        registry = new InMemoryRegistry(config);
    }

    @Test
    void shouldAllowRequestsWithinCapacity() {
        for (int i = 0; i < 5; i++) {
            assertTrue(registry.consume("user:meeth"));
        }
    }

    @Test
    void shouldRejectWhenBucketEmpty() {
        for (int i = 0; i < 5; i++) {
            registry.consume("user:meeth");
        }
        assertFalse(registry.consume("user:meeth"));
    }

    @Test
    void shouldIsolatePerUser() {
        for (int i = 0; i < 5; i++) {
            registry.consume("user:meeth");
        }
        // meeth is exhausted but alice is fresh
        assertTrue(registry.consume("user:alice"));
    }

    @Test
    void shouldResetCorrectly() {
        for (int i = 0; i < 5; i++) {
            registry.consume("user:meeth");
        }
        registry.reset("user:meeth");
        assertTrue(registry.consume("user:meeth"));
    }

    @Test
    void shouldAllowBulkConsume() {
        assertTrue(registry.consume("user:meeth", 5));
    }

    @Test
    void shouldRejectBulkConsumeWhenInsufficientTokens() {
        registry.consume("user:meeth", 3);
        assertFalse(registry.consume("user:meeth", 3));
    }
}