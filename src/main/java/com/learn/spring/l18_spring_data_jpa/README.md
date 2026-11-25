# Lesson 18: Spring Data JPA (repositories without implementations)

Lesson 17 wrote `em.createQuery(...)` by hand. Spring Data generates that code from an **interface**.

## Concepts
- `interface CustomerRepository extends JpaRepository<Customer, Long>` gives CRUD, sorting and paging for free.
- **Derived queries**: the method name is parsed into a query.
  `findBy`, `countBy`, `existsBy`, `deleteBy` + properties + `And`/`Or`, `Between`, `LessThan`, `Like`, `StartingWith`,
  `IgnoreCase`, `True`/`False`, `IsNull`, `In`, `OrderBy...`.
- **@Query**: write JPQL (or `nativeQuery = true` SQL) when a name would get too long.
- **@Modifying**: bulk `UPDATE`/`DELETE` queries. They skip dirty checking and the persistence context.
- **Paging and sorting**: `Pageable`, `PageRequest.of(page, size, sort)`, `Page<T>` (content, totals, `nextPageable()`).
- **Projections**: interface-based (`CustomerSummary`) or DTO-based (`CityCount`). They fetch only what you need.
- **Auditing**: `@EnableJpaAuditing`, `@CreatedDate`, `@LastModifiedDate` (also `@CreatedBy` with an `AuditorAware`).
- Repository methods are **transactional**: reads are read-only and `save`/`delete` write.
- **Specifications** (`CustomerSpecs`): reusable criteria pieces combined at runtime with `and`/`or`/`not`.
  The answer to search screens with many **optional** filters (`matching(CustomerFilter)`), which would otherwise need a
  method for every combination. The repository extends `JpaSpecificationExecutor`; paging and sorting still work.
- **Query by Example**: pass a half-filled `Customer` as a probe plus an `ExampleMatcher` (ignore case, `CONTAINING`).
  Simple, but no ranges or `OR`s. Ignore primitive fields explicitly: `age = 0` would otherwise be part of the query.
- **`@EntityGraph`**: fetch associations in the same query (N+1 fix, see lesson 17). Shown in `server_spring_boot` module 04.

## Which query style?
| Need | Use |
|---|---|
| Fixed, simple condition | derived query (`findByCityAndActiveTrue`) |
| Fixed, complex (joins, grouping) | `@Query` JPQL, or native SQL |
| Conditions chosen at runtime | `Specification` (or Querydsl) |
| Reports, heavy SQL | `JdbcClient` (lesson 15) next to JPA |

## `save()` = persist or merge
For a new entity (`id == null`) `save` calls `persist`. Otherwise it calls `merge`.
Inside a transaction you don't need `save()` for changes to managed entities (dirty checking, lesson 17).

## Run
```
mvn -q compile exec:java -Dexec.args=18
```
