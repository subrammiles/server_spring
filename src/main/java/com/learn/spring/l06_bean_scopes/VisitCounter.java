package com.learn.spring.l06_bean_scopes;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * SINGLETON scope (the default): ONE instance shared by everybody.
 * Shared means it must be thread-safe, hence AtomicInteger instead of int.
 */
@Component
public class VisitCounter {
    private final AtomicInteger visits = new AtomicInteger();

    public int visit() {
        return visits.incrementAndGet();
    }
}
