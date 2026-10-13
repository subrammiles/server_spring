# Spring Framework from scratch to advanced (without Spring Boot)

A hands-on course in 20 lessons. Each lesson is a package with:
- a **README.md** explaining the concepts,
- small, focused **example classes** and a runnable **`*Demo`**,
- **tests** that prove the behaviour (and teach testing).

Spring Boot hides most of what is taught here behind auto-configuration. Learning plain Spring first
shows what Boot does for you. Continue with the `server_spring_boot` repository afterwards.

## Requirements
- Java 21+ (tested on Java 25)
- Maven 3.8+
- Nothing else. The databases are in-memory H2, and the web server is an embedded Tomcat.

## Run
```bash
mvn -q compile exec:java                    # list lessons
mvn -q compile exec:java -Dexec.args=05     # run lesson 05's demo
mvn test                                    # run all 64 tests
```
Or open the project in IntelliJ/Eclipse/VS Code and run any `*Demo` class's `main` method.

## Roadmap

### Part 1: The core container
| # | Lesson | You will learn |
|---|---|---|
| 01 | [Life without Spring](src/main/java/com/learn/spring/l01_without_spring) | coupling, interfaces, dependency injection by hand, IoC |
| 02 | [XML configuration](src/main/java/com/learn/spring/l02_xml_config) | ApplicationContext, beans, constructor/setter injection, collections, aliases |
| 03 | [Java configuration](src/main/java/com/learn/spring/l03_java_config) | `@Configuration`, `@Bean`, `@Import`, CGLIB proxies for inter-bean calls |
| 04 | [Component scanning](src/main/java/com/learn/spring/l04_component_scan) | stereotypes, `@ComponentScan`, constructor vs setter vs field injection, layering |
| 05 | [Choosing between beans](src/main/java/com/learn/spring/l05_autowiring_choices) | `@Primary`, `@Qualifier`, custom qualifiers, `List`/`Map` injection, `ObjectProvider` |
| 06 | [Scopes](src/main/java/com/learn/spring/l06_bean_scopes) | singleton, prototype, web scopes, the prototype-in-singleton trap, `@Lazy` |
| 07 | [Lifecycle](src/main/java/com/learn/spring/l07_bean_lifecycle) | `@PostConstruct`/`@PreDestroy`, Aware interfaces, `BeanPostProcessor`, `BeanFactoryPostProcessor` |
| 08 | [Externalized config](src/main/java/com/learn/spring/l08_externalized_config) | `@PropertySource`, `@Value`, `Environment`, SpEL, `Resource`, i18n `MessageSource` |
| 09 | [Profiles and conditions](src/main/java/com/learn/spring/l09_profiles_conditions) | `@Profile`, `@Conditional`, how Boot auto-configuration works |
| 10 | [Events](src/main/java/com/learn/spring/l10_events) | `ApplicationEventPublisher`, `@EventListener`, ordering, conditions, chaining |
| 11 | [AOP](src/main/java/com/learn/spring/l11_aop) | aspects, pointcuts, all advice types, custom annotations, proxy limits |
| 12 | [Validation](src/main/java/com/learn/spring/l12_validation) | Jakarta Bean Validation, nested and custom constraints, method validation |
| 13 | [Async and scheduling](src/main/java/com/learn/spring/l13_async_scheduling) | `@Async`, `CompletableFuture`, thread pools, `@Scheduled`, cron |
| 14 | [Caching](src/main/java/com/learn/spring/l14_caching) | `@Cacheable`, `@CachePut`, `@CacheEvict`, cache keys and conditions |

### Part 2: Data access
| # | Lesson | You will learn |
|---|---|---|
| 15 | [Spring JDBC](src/main/java/com/learn/spring/l15_jdbc) | `DataSource`, `JdbcTemplate`, named parameters, `JdbcClient`, batch, exception translation |
| 16 | [Transactions](src/main/java/com/learn/spring/l16_transactions) | ACID, `@Transactional`, rollback rules, propagation, `TransactionTemplate`, pitfalls |
| 17 | [JPA and Hibernate](src/main/java/com/learn/spring/l17_jpa_hibernate) | entities, relationships, persistence context, dirty checking, lazy loading, N+1 |
| 18 | [Spring Data JPA](src/main/java/com/learn/spring/l18_spring_data_jpa) | repositories, derived queries, `@Query`, paging, projections, auditing |

### Part 3: Web
| # | Lesson | You will learn |
|---|---|---|
| 19 | [REST with Spring MVC](src/main/java/com/learn/spring/l19_web_mvc) | `DispatcherServlet`, embedded Tomcat, REST controllers, validation, `ProblemDetail` errors, interceptors, CORS |

### Part 4: Testing
| # | Lesson | You will learn |
|---|---|---|
| 20 | [Testing](src/test/java/com/learn/spring/l20_testing) | Mockito unit tests, Spring TestContext, `@MockitoBean`, rollback, `@Sql`, MockMvc, context caching |

## Tips for learning
1. Read the lesson README, run the demo, then read the code.
2. Do the "Try it" exercises, and break things on purpose. Spring's error messages are part of the lesson.
3. Read the matching test. Change an assertion and watch it fail.

## Versions
Spring Framework 7.0, Spring Data 2025.1, Hibernate ORM 7.2, Hibernate Validator 9, Jakarta EE 11, Tomcat 11, Jackson 3,
JUnit 6, Mockito 5. They are managed through BOMs in `pom.xml`.
