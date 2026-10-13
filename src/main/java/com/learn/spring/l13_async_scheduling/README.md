# Lesson 13: Asynchronous methods and scheduled tasks

## @Async
- `@EnableAsync` plus `@Async` on a method: the call returns immediately and the body runs on a thread pool.
- Return `void` (fire-and-forget) or `CompletableFuture<T>` (the caller can wait and combine results).
- Configure the pool with a `ThreadPoolTaskExecutor` bean named `taskExecutor` (size, queue, thread names).
- **Proxy based** (lesson 11): self-invocation runs synchronously, and `private` methods are ignored.
- Exceptions in `void` async methods are invisible to the caller. Register an `AsyncUncaughtExceptionHandler`.
- **Java 21+ virtual threads**: `new SimpleAsyncTaskExecutor()` with `setVirtualThreads(true)` gives cheap threads for I/O-heavy work.
- `@Async` + `@EventListener`: asynchronous event listeners (lesson 10).

## @Scheduled
- `@EnableScheduling` plus `@Scheduled(...)` on a no-arg method.
- `fixedRate` (start to start), `fixedDelay` (end to start), `initialDelay`, `cron = "sec min hour day month weekday"`.
- `...String` variants read values from properties: `fixedRateString = "${heartbeat.rate-ms:500}"`.
- Runs on a single scheduler thread by default, so one slow task delays the others.
- With several app instances every instance runs the job. Use a lock (e.g. ShedLock) or a real job scheduler.

## Run
```
mvn -q compile exec:java -Dexec.args=13
```
