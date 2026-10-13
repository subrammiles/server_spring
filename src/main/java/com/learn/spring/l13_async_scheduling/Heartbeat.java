package com.learn.spring.l13_async_scheduling;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Heartbeat {
    private final AtomicInteger beats = new AtomicInteger();

    /** Every 500 ms (configurable). fixedRate = from start to start; fixedDelay = from end to start. */
    @Scheduled(fixedRateString = "${heartbeat.rate-ms:500}")
    public void beat() {
        int n = beats.incrementAndGet();
        System.out.println("  heartbeat #" + n + " on " + Thread.currentThread().getName());
    }

    /** Cron: second minute hour day month weekday. "0 0 2 * * MON-FRI" = 02:00 on weekdays. */
    @Scheduled(cron = "0 0 2 * * MON-FRI")
    public void nightlyCleanup() {
        System.out.println("  nightly cleanup");
    }

    public int beats() {
        return beats.get();
    }
}
