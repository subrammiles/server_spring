package com.learn.spring.l13_async_scheduling;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    /** Returns immediately. The work runs on the "async-" pool and completes the future later. */
    @Async
    public CompletableFuture<String> generate(String name) {
        sleep(500);
        return CompletableFuture.completedFuture(name + " built on " + Thread.currentThread().getName());
    }

    /** Fire-and-forget: the caller does not wait and cannot see exceptions (use an AsyncUncaughtExceptionHandler). */
    @Async
    public void sendEmail(String to) {
        sleep(100);
        System.out.println("  email sent to " + to + " on " + Thread.currentThread().getName());
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
