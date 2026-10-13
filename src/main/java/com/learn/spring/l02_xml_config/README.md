# Lesson 02: The IoC container with XML configuration

## Concepts
- **Bean**: an object created and managed by Spring.
- **ApplicationContext**: the IoC container. `ClassPathXmlApplicationContext` builds one from an XML file.
- **Bean definition**: the recipe (`<bean id=".." class="..">`) Spring uses to build a bean.
- **Constructor injection**: `<constructor-arg ref="..."/>`
- **Setter injection**: `<property name="..." value="..."/>` or `ref="..."`, plus `<list>`, `<map>`, `<set>` for collections.
- **getBean** by name, by type, or both.
- **Singleton by default**: asking twice gives the same instance.
- **Alias**: an extra name for a bean.

## Constructor vs setter injection
| Constructor | Setter |
|---|---|
| Required dependencies | Optional dependencies |
| Fields can be `final` (immutable) | Object can be changed after creation |
| Object is never half-built | Object can exist without its dependency set |

Prefer **constructor injection**. Modern Spring code (lesson 04+) uses it almost everywhere.

## Why learn XML at all?
You will meet it in older projects. The concepts (definitions, injection, scopes) are the same in every style.
Modern code uses Java config (lesson 03) and annotations (lesson 04).

## Run
```
mvn -q compile exec:java -Dexec.args=02
```

## Try it
- Add a `<map>` property to `SecuritySystem` (zone name -> alarm).
- Remove `<constructor-arg>` from `frontDoor` and read the error: Spring fails *at startup*, not later.
