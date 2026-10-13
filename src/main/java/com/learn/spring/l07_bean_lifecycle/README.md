# Lesson 07: Bean lifecycle and extension points

## The full lifecycle of a singleton (run the demo to see it live)
```
 BeanFactoryPostProcessor        <- edits bean DEFINITIONS, before any bean exists
 1. constructor
 2. dependency injection          (@Autowired fields/setters)
 3. Aware callbacks              (BeanNameAware, ApplicationContextAware, ...)
 4. BeanPostProcessor.before...
 5. @PostConstruct
 6. InitializingBean.afterPropertiesSet()
 7. @Bean(initMethod = "...")
 8. BeanPostProcessor.after...   <- AOP proxies are created here
 ... bean is used ...
 9. @PreDestroy                  (on context.close())
10. DisposableBean.destroy()
11. @Bean(destroyMethod = "...")
```

## Which init/destroy style should I use?
- **`@PostConstruct` / `@PreDestroy`**: the standard choice for your own classes.
- **`@Bean(initMethod, destroyMethod)`**: for third-party classes you cannot annotate.
- **`InitializingBean` / `DisposableBean`**: avoid. They tie your class to Spring interfaces.
- Do **not** do real work in the constructor if it needs injected (setter/field) dependencies: they are still `null` there.

## Extension points
- **BeanPostProcessor**: wraps or modifies every bean (Spring's AOP, `@Async`, `@Autowired` are built on it).
- **BeanFactoryPostProcessor**: modifies bean definitions (property placeholders, for example).
- **Aware interfaces**: give a bean access to container internals. Use them rarely.
- **SmartLifecycle**: start/stop hooks for components such as message listeners (not shown).

## Run
```
mvn -q compile exec:java -Dexec.args=07
```
