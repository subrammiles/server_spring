# Lesson 04: Component scanning and @Autowired

## Concepts
- **Stereotype annotations**: all of them make a class a bean. The name documents the layer.
  | Annotation | Layer | Extra behaviour |
  |---|---|---|
  | `@Component` | generic | - |
  | `@Service` | business logic | - |
  | `@Repository` | data access | translates DB exceptions to Spring's `DataAccessException` |
  | `@Controller` / `@RestController` | web (lesson 19) | request handling |
- **@ComponentScan**: finds those classes in a package and its sub-packages.
  With no argument it scans the package of the class it is on.
- **Default bean name**: `OrderService` becomes `orderService`. Override it with `@Component("myName")`.
- **Three injection styles**: constructor (`OrderService`), setter (`ReportService`), field (`LegacyAuditor`).
- **Single constructor** means `@Autowired` is optional.
- **Layered architecture**: Repository, Service, (Controller). Each layer depends only on the one below.

## Rule of thumb
Use **constructor injection** with `final` fields. It is explicit, immutable and easy to test (lesson 20 shows
`OrderService` tested with Mockito and no Spring at all).

## Run
```
mvn -q compile exec:java -Dexec.args=04
```

## Try it
- Remove `@Repository` from `InMemoryOrderRepository` and read the `NoSuchBeanDefinitionException`.
- Add a second constructor to `OrderService`. Spring no longer knows which to use. Add `@Autowired` to pick one.
