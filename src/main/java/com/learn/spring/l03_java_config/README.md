# Lesson 03: Java-based configuration (@Configuration and @Bean)

## Concepts
- **@Configuration**: marks a class as a source of bean definitions (the Java replacement for `beans.xml`).
- **@Bean**: the method's return value becomes a bean. The method name is the bean name.
- **AnnotationConfigApplicationContext**: a container built from configuration classes.
- **Injecting into @Bean methods**: add a parameter. Spring finds a matching bean and passes it in.
- **@Qualifier("name")**: chooses between several beans of the same type (more in lesson 05).
- **Aliases**: `@Bean(name = {"a", "b"})`.
- **@Import**: combines configuration classes, like `<import>` in XML.
- **Inter-bean calls**: inside a `@Configuration` class, calling another `@Bean` method returns the
  managed singleton, because Spring subclasses the class at runtime (CGLIB proxy).
  `@Configuration(proxyBeanMethods = false)` turns this off for faster startup. Then use parameters instead of calls.

## When to use @Bean vs @Component (lesson 04)
- `@Bean`: for classes you **cannot annotate** (third-party: `DataSource`, `ObjectMapper`...) or when creation needs logic.
- `@Component`: for your **own** classes.

## Run
```
mvn -q compile exec:java -Dexec.args=03
```

## Try it
- Add `@Configuration(proxyBeanMethods = false)` to `DoorConfig` and check whether `sideDoor` and `cellarDoor` still share one alarm.
