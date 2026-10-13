package com.learn.spring.l13_async_scheduling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AsyncConfig.class)
@TestPropertySource(properties = "heartbeat.rate-ms=20")   // speed the scheduler up for the test
class AsyncSchedulingTest {

    @Autowired
    ReportService reports;

    @Autowired
    Heartbeat heartbeat;

    @Test
    void asyncMethodsRunInParallelOnThePool() {
        long start = System.currentTimeMillis();
        CompletableFuture<String> a = reports.generate("a");
        CompletableFuture<String> b = reports.generate("b");

        assertThat(a.join()).contains("async-");
        assertThat(b.join()).contains("async-");
        assertThat(System.currentTimeMillis() - start).isLessThan(950);   // sequential would be >= 1000
    }

    @Test
    void scheduledMethodKeepsRunning() throws InterruptedException {
        int before = heartbeat.beats();
        long deadline = System.currentTimeMillis() + 3000;
        while (heartbeat.beats() < before + 3 && System.currentTimeMillis() < deadline) {
            Thread.sleep(20);
        }
        assertThat(heartbeat.beats()).isGreaterThanOrEqualTo(before + 3);
    }
}
