package com.learn.spring.l13_async_scheduling;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AsyncSchedulingDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AsyncConfig.class)) {
            ReportService reports = context.getBean(ReportService.class);

            System.out.println("--- 3 reports of 500 ms each, in parallel ---");
            long start = System.currentTimeMillis();
            List<CompletableFuture<String>> futures = List.of(
                    reports.generate("sales"), reports.generate("stock"), reports.generate("hr"));
            System.out.println("calls returned after " + (System.currentTimeMillis() - start) + " ms (nothing done yet)");
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
            futures.forEach(f -> System.out.println("  " + f.join()));
            System.out.println("all done after " + (System.currentTimeMillis() - start) + " ms (not 1500)");

            System.out.println("\n--- fire and forget ---");
            reports.sendEmail("alice@example.com");
            System.out.println("main thread moves on immediately");

            System.out.println("\n--- @Scheduled heartbeat for ~1.6 seconds ---");
            ReportService.sleep(1600);
        }
    }
}
