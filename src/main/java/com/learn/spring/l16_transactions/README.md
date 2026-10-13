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

## Pitfalls (all because it is proxy-based AOP, lesson 11)
- Calling a `@Transactional` method from **the same class** (`this.x()`) ignores the annotation.
- `@Transactional` on `private` methods does nothing.
- Catching the exception **inside** the transactional method means Spring never sees it, so it commits.

## Run
```
mvn -q compile exec:java -Dexec.args=16
```
