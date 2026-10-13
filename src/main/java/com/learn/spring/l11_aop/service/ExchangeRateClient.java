package com.learn.spring.l11_aop.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.learn.spring.l11_aop.Retry;

/** Simulates a flaky remote API: the first two calls fail, the third succeeds. */
@Service
public class ExchangeRateClient {
    private final AtomicInteger calls = new AtomicInteger();

    @Retry(attempts = 3)
    public double usdToInr() {
        if (calls.incrementAndGet() < 3) {
            throw new IllegalStateException("network timeout (call " + calls.get() + ")");
        }
        return 83.2;
    }

    public int calls() {
        return calls.get();
    }
}
