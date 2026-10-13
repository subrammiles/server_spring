# Lesson 10: Application events (loose coupling inside one app)

`OrderService` publishes an `OrderPlacedEvent`. Inventory, email, fraud and loyalty code react to it
without `OrderService` knowing they exist. This is the **observer / publish-subscribe** pattern.

## Concepts
- **Publish**: inject `ApplicationEventPublisher` and call `publishEvent(anyObject)`.
- **Listen**: put `@EventListener` on a method whose parameter is the event type.
- **Ordering**: `@Order(n)`. Lower runs first.
- **Conditional listener**: `@EventListener(condition = "#event.amount > 1000")` (SpEL).
- **Chaining**: a listener's return value is published as a new event.
- **Built-in events**: `ContextRefreshedEvent`, `ContextClosedEvent`, `ContextStartedEvent`...
- **Synchronous by default**: listeners run in the publisher's thread, and a listener exception propagates to the publisher.
  Add `@Async` (lesson 13) for fire-and-forget.
- **Transactions**: `@TransactionalEventListener` waits until the publishing transaction commits
  (send the email only if the order was really saved).
- **Older style**: `implements ApplicationListener<MyEvent>` with `MyEvent extends ApplicationEvent`. Still works, just more verbose.

## Events vs direct calls vs message brokers
- Direct method call: simple, but the caller depends on every callee.
- Spring events: decoupled, in-memory, same JVM. Lost if the app crashes.
- Kafka/RabbitMQ: decoupled across services, durable. See the `server_kafka` repo.

## Run
```
mvn -q compile exec:java -Dexec.args=10
```
