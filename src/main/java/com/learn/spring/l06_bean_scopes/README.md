# Lesson 06: Bean scopes and lazy initialization

## Scopes
| Scope | Instances | Typical use |
|---|---|---|
| `singleton` (default) | one per container | stateless services, repositories |
| `prototype` | new one per lookup / injection | stateful, short-lived objects |
| `request` / `session` / `application` | one per HTTP request / session / ServletContext | web apps only |
| `websocket` | one per WebSocket session | web apps only |

Declare with `@Scope("prototype")` or `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`.

## Key points
- **Singletons are shared between threads**: keep them stateless, or use thread-safe state (`AtomicInteger`, concurrent collections).
- **Spring does not destroy prototypes**: `@PreDestroy` is never called for them. You own their cleanup.
- **Prototype inside a singleton** is injected once, so it is effectively a singleton (see `BrokenCheckoutService`).
  Fixes: `ObjectProvider<T>` (shown), `@Lookup` methods, or scoped proxies (`proxyMode = ScopedProxyMode.TARGET_CLASS`).
- **Eager vs lazy**: singletons are created at startup, which surfaces errors early. `@Lazy` defers creation.

## Run
```
mvn -q compile exec:java -Dexec.args=06
```

## Try it
- Replace `ObjectProvider` in `CheckoutService` with an abstract method annotated `@Lookup`.
