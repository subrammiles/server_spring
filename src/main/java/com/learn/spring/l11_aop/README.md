# Lesson 11: Aspect-Oriented Programming (AOP)

Logging, timing, security checks, retries and transactions are needed in many classes but are not their business logic.
These are **cross-cutting concerns**. AOP writes them once and applies them where they match.
`BankAccountService` contains no logging or timing code at all.

## Vocabulary
| Term | Meaning | Here |
|---|---|---|
| Aspect | a class holding cross-cutting logic | `LoggingAspect`, `TimingAspect`, `RetryAspect` |
| Join point | a point in execution (in Spring: a method call) | `deposit(...)` |
| Pointcut | expression selecting join points (WHERE) | `within(..service..*)`, `@annotation(...)` |
| Advice | the code to run (WHAT and WHEN) | `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around` |
| Proxy | object Spring puts in front of your bean | `BankAccountService$$SpringCGLIB$$0` |

## Pointcut cheat sheet
```
execution(* com.app.service.*.*(..))     any method of any class in com.app.service
execution(public String *.find*(Long))   public methods returning String named find*, one Long arg
within(com.app..*)                       anything in com.app and sub-packages
@annotation(com.app.LogExecutionTime)    methods carrying the annotation
bean(*Service)                           beans whose name ends with Service
```

## How it works and its limits
`@EnableAspectJAutoProxy` adds a **BeanPostProcessor** (lesson 07) that replaces matching beans with **proxies**.
Advice runs only when a call goes **through the proxy**, so:
- **Self-invocation** (`this.method()`) is not intercepted (see `yearlyReport`).
- `private` and `final` methods cannot be intercepted.
- The same limits apply to `@Transactional` (lesson 16), `@Async` (13) and `@Cacheable` (14). They are all AOP.

## Run
```
mvn -q compile exec:java -Dexec.args=11
```

## Try it
- Write an `@After` advice (runs like `finally`).
- Write a `@Secured("ADMIN")` annotation plus an aspect that throws unless a thread-local "current user" is an admin.
