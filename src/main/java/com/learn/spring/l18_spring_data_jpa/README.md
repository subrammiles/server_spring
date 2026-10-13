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
- Not shown, worth knowing: `Specification` (dynamic criteria queries), Query by Example, `@EntityGraph`.

## `save()` = persist or merge
For a new entity (`id == null`) `save` calls `persist`. Otherwise it calls `merge`.
Inside a transaction you don't need `save()` for changes to managed entities (dirty checking, lesson 17).

## Run
```
mvn -q compile exec:java -Dexec.args=18
```
