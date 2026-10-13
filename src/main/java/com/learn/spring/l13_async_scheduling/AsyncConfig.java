package com.learn.spring.l13_async_scheduling;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan
@EnableAsync        // turns on @Async (proxy based)
@EnableScheduling   // turns on @Scheduled
public class AsyncConfig {

    /**
     * The pool @Async methods run on. The bean name "taskExecutor" makes it the default.
     * Without it Spring uses a SimpleAsyncTaskExecutor (a new thread per task, no limit).
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}
