package io.github.meeth.ratelimiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowLimiterTest {

    private InMemoryRegistry registry;

    @BeforeEach
    void setUp() {
        RateLimiterConfig config = new RateLimiterConfig(Algorithm.SLIDING_WINDOW, 5, 1);
        registry = new InMemoryRegistry(config);
    }

    @Test
    void shouldAllowRequestsWithinCapacity() {
        for (int i = 0; i < 5; i++) {
            assertTrue(registry.consume("user:meeth"));
        }
    }

    @Test
    void shouldRejectWhenWindowFull() {
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
    void shouldAllowRequestsAfterWindowExpires() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            registry.consume("user:meeth");
        }
        // wait for window to expire (1 second)
        Thread.sleep(1100);
        assertTrue(registry.consume("user:meeth"));
    }

    @Test
    void shouldRejectBulkConsumeWhenWindowFull() {
        registry.consume("user:meeth", 3);
        assertFalse(registry.consume("user:meeth", 3));
    }
}