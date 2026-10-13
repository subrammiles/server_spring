# Lesson 12: Validation (Jakarta Bean Validation)

## Concepts
- **Declarative constraints** on fields and record components: `@NotNull`, `@NotBlank`, `@Size`, `@Min`/`@Max`,
  `@Email`, `@Pattern`, `@Past`/`@Future`, `@Positive`...
- **Cascading**: `@Valid` on a nested object validates its constraints too.
- **Custom constraint**: an annotation (`@StrongPassword`) plus a `ConstraintValidator`.
- **Programmatic validation**: `validator.validate(obj)` returns a set of `ConstraintViolation`s.
- **Method validation**: `@Validated` on a bean together with `MethodValidationPostProcessor` checks parameters
  (and return values) and throws `ConstraintViolationException`. It is proxy based, so the lesson 11 limits apply.
- **Validation groups**: validate different rules for "create" vs "update" (`groups = OnCreate.class`). Not shown.
- In web apps (lesson 19), `@Valid @RequestBody` validates incoming JSON automatically and returns HTTP 400.

`jakarta.validation` is the specification and **Hibernate Validator** is the implementation (not related to Hibernate ORM).
Spring's `LocalValidatorFactoryBean` connects it to the container.

## Run
```
mvn -q compile exec:java -Dexec.args=12
```
