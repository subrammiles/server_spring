# Lesson 01: Life without Spring (why DI exists)

## Concepts
- **Coupling**: `TightlyCoupledDoor` calls `new SoundAlarm()` itself, so it is welded to one implementation.
- **Program to an interface**: `Door` depends on `Alarm`, not on a concrete class.
- **Dependency Injection (DI)**: an object *receives* its dependencies (here through the constructor) instead of creating them.
- **Inversion of Control (IoC)**: the object no longer controls how its dependencies are created. Someone else does.
  In `WithoutSpringDemo.main` that someone is *you*. From lesson 02 on it is the **Spring IoC container**.

## Why bother?
| Problem with `new` everywhere | DI fixes it |
|---|---|
| Changing an implementation means editing many classes | Change the wiring in one place |
| Hard to unit test (real DB, real email...) | Pass a fake/mock in tests |
| Hidden dependencies | Constructor shows exactly what a class needs |

## Run
```
mvn -q compile exec:java -Dexec.args=01
```

## Try it
1. Add a `SilentAlarm` class and give it to a new door without touching `Door`.
2. Imagine an app with 200 classes. Who writes all that wiring code? That is what Spring is for.
