# Lesson 05: Several beans of one type (choosing and collecting)

With one implementation, `@Autowired` is easy. With three `PaymentGateway`s, Spring fails with
`NoUniqueBeanDefinitionException` unless you tell it which one you want.

## Ways to choose
| Technique | Where | Example |
|---|---|---|
| `@Primary` | on the bean | `StripeGateway`: the default choice |
| `@Qualifier("name")` | at the injection point | `@Qualifier("paypal")` |
| Custom qualifier annotation | both sides | `@Experimental`: type-safe, no strings |
| Parameter name = bean name | injection point | `PaymentGateway paypal` (needs the `-parameters` compiler flag, already set in `pom.xml`) |

## Ways to collect
- `List<PaymentGateway>`: every implementation, sorted by `@Order` / `Ordered`. This is the **strategy pattern** for free.
- `Map<String, PaymentGateway>`: bean name to bean. Pick an implementation at runtime.

## Optional dependencies
- `ObjectProvider<T>`: `getIfAvailable()`, `getIfUnique()`, `orderedStream()`. It is also lazy.
- `Optional<T>`, or `@Autowired(required = false)`.

## Run
```
mvn -q compile exec:java -Dexec.args=05
```

## Try it
- Remove `@Primary` from `StripeGateway` and read the startup error.
- Add a `FraudChecker` `@Component` that flags amounts above 1000 and call `checkout(5000)`.
