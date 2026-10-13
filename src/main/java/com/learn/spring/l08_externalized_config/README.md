# Lesson 08: Externalized configuration, SpEL, resources and i18n

Hard-coded values (URLs, limits, passwords) mean recompiling for every environment.
Spring reads them from outside the code instead.

## Concepts
- **@PropertySource("classpath:...")**: loads a `.properties` file into the **Environment**.
- **`${key}`**: placeholder for a property. **`${key:default}`** adds a fallback.
- **`#{expression}`**: **SpEL** (Spring Expression Language): maths, method calls, other beans (`#{appSettings.discountPercent}`), `systemProperties`.
- **Type conversion**: `"100"` becomes `int`, `"a,b,c"` becomes `String[]`, `"classpath:x.txt"` becomes `Resource`.
- **Environment**: `getProperty`, `getRequiredProperty`, typed lookups, defaults.
- **Property precedence**: `-Dkey=value` system properties override OS environment variables, which override `@PropertySource` files.
- **Resource**: one API for `classpath:`, `file:` and `https:` locations.
- **MessageSource**: internationalisation (i18n) through `messages_<locale>.properties`.
- **SpEL standalone**: `SpelExpressionParser`, with selection `.?[]` and projection `.![]`.

## Secrets
Never commit passwords in property files. Read them from environment variables or a secret manager.
(The old version of this repo had a database password committed in `MainActivity.java`.)

## Spring Boot preview
Boot adds `application.properties`/`.yml` loading and type-safe `@ConfigurationProperties` on top of exactly this mechanism.

## Run
```
mvn -q compile exec:java -Dexec.args=08
mvn -q compile exec:java -Dexec.args=08 -Dapp.name="Overridden from the command line"
```
