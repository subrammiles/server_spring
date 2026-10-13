# Lesson 09: Profiles and conditional beans

One codebase, different environments: local disk in dev, S3 in prod, fakes in tests.

## Profiles
- `@Profile("prod")`: the bean exists only when the `prod` profile is active.
- `default` profile: active when **no** profile is set.
- Expressions: `"!prod"`, `"dev | test"`, `"prod & eu"`.
- Activate with `-Dspring.profiles.active=prod`, `context.getEnvironment().setActiveProfiles("prod")`,
  or `@ActiveProfiles` in tests (lesson 20).

## Conditions
- `@Conditional(SomeCondition.class)`: register a bean only when `Condition.matches(...)` returns true.
- `@ConditionalOnFeature("recommendations")` is a custom annotation that reads `features.recommendations=true`.
- **This is the engine behind Spring Boot auto-configuration** (`@ConditionalOnClass`,
  `@ConditionalOnProperty`, `@ConditionalOnMissingBean`...). After this lesson Boot's "magic" is just conditions.

## Run
```
mvn -q compile exec:java -Dexec.args=09
```

## Try it
- Add an `InMemoryStorage` for a `test` profile and use it from a test with `@ActiveProfiles("test")`.
