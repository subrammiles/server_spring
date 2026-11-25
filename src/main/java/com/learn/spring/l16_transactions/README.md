# Lesson 16: Transactions

A money transfer is two updates. If the second one fails, the first must be undone.
A **transaction** makes a group of operations **atomic**: all succeed, or none do.

## ACID
**A**tomic (all or nothing), **C**onsistent (rules hold), **I**solated (concurrent transactions don't see each
other's half-done work), **D**urable (committed means saved).

## Declarative transactions
- `@EnableTransactionManagement` plus a `PlatformTransactionManager` bean
  (`DataSourceTransactionManager` for JDBC, `JpaTransactionManager` for JPA).
- `@Transactional` on a public method (or the class): commit on success, roll back on **RuntimeException/Error**.
- **Checked exceptions commit by default** (scenario 3). Use `rollbackFor = X.class`.
- `readOnly = true`: an optimisation hint for queries.
- **Propagation** (what happens when a transactional method calls another):
  | Propagation | Behaviour |
  |---|---|
  | `REQUIRED` (default) | join the existing transaction or start one |
  | `REQUIRES_NEW` | suspend the current one and run in a new one (the audit log) |
  | `MANDATORY` | fail if there is no transaction |
  | `SUPPORTS` / `NOT_SUPPORTED` / `NEVER` / `NESTED` | rarer |
- **Isolation**: `READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`. Higher means safer and slower.
- `timeout = 5`: roll back if the transaction runs too long.

## Programmatic transactions
`TransactionTemplate.execute(status -> ...)`. Use `status.setRollbackOnly()` to roll back without an exception.

## Events and transactions (`PaymentNotifications`)
`BulkPaymentService` publishes a `PaymentSent` event for each payment, inside the transaction.
| Listener | Runs | After a rollback |
|---|---|---|
| `@EventListener` | immediately, mid-transaction | has **already** sent the SMS for money that never moved |
| `@TransactionalEventListener` (AFTER_COMMIT) | after the commit | never runs: correct |
| `@TransactionalEventListener(phase = AFTER_ROLLBACK)` | after the rollback | runs: good for alerts and cleanup |

Things to know:
- With **no** transaction active, a `@TransactionalEventListener` silently drops the event (`fallbackExecution = true` changes that).
- An AFTER_COMMIT listener that writes to the database needs its own transaction (`REQUIRES_NEW`): the original one is finished.
- If the process dies between the commit and the listener, the event is **lost**. When it must not be lost (sending to Kafka,
  emailing invoices), use the **transactional outbox** pattern: insert the event into an `outbox` table in the same
  transaction, and let a separate job publish it. Spring Modulith's event publication registry does this for you.

## Pitfalls (all because it is proxy-based AOP, lesson 11)
- Calling a `@Transactional` method from **the same class** (`this.x()`) ignores the annotation.
- `@Transactional` on `private` methods does nothing.
- Catching the exception **inside** the transactional method means Spring never sees it, so it commits.

## Run
```
mvn -q compile exec:java -Dexec.args=16
```
