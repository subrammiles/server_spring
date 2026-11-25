# Lesson 08: Externalized configuration, SpEL, resources and i18n

Hard-coded values (URLs, limits, passwords) mean recompiling for every environment.
Spring reads them from outside the code instead.

## Concepts
- **@PropertySource("classpath:...")**: loads a `.properties` file into the **Environment**.
- **`${key}`**: placeholder for a property. **`${key:default}`** adds a fallback.
- **`#{expression}`**: **SpEL** (Spring Expression Language): maths, method calls, other beans (`#{appSettings.discountPercent}`), `systemProperties`.
- **Type conversion**: `"100"` becomes `int`, `"a,b,c"` becomes `String[]`, `"classpath:x.txt"` becomes `Resource`.
- **ConversionService** (`ShippingSettings`, `ConfigConfig`): the engine behind that conversion. Register a bean named
  `conversionService` to extend it:
  | Piece | Direction | Example here |
  |---|---|---|
  | `Converter<S, T>` | one way, any types | `MoneyConverter`: `"EUR 4.99"` -> `Money` |
  | `Formatter<T>` | `String` <-> `T`, locale-aware (for UIs and web forms) | built-in, picked by `@DateTimeFormat`, `@NumberFormat`, `@DurationFormat` |
  | `ConversionService.convert(...)` | call it yourself | `conversion.convert("usd 10.50", Money.class)` |
  The same service converts web request parameters (lesson 19). Boot's `@ConfigurationProperties` builds on it too.
- **Environment**: `getProperty`, `getRequiredProperty`, typed lookups, defaults.
- **Property precedence**: `-Dkey=value` system properties override OS environment variables, which override `@PropertySource` files.
- **Resource**: one API for `classpath:`, `file:` and `https:` locations.
- **MessageSource**: internationalisation (i18n) through `messages_<locale>.properties`.
- **SpEL standalone**: `SpelExpressionParser`, with selection `.?[]` and projection `.![]`.

## Try it
- Set `app.shipping.fee=4.99` and read the startup error: conversion failures stop the context, which is what you want.
- Remove the `conversionService` bean. Which `@Value` injections fail, and which still work?

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
