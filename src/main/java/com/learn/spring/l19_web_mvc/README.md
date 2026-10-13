# Lesson 19: REST APIs with Spring MVC (on an embedded Tomcat, no Spring Boot)

## How a request flows
```
HTTP request -> Tomcat -> DispatcherServlet -> HandlerInterceptor.preHandle
             -> HandlerMapping finds the @GetMapping/@PostMapping method
             -> arguments resolved (@PathVariable, @RequestParam, @RequestBody + @Valid, @RequestHeader)
             -> controller -> service -> ...
             -> return value -> HttpMessageConverter (Jackson) -> JSON response
   exception? -> @RestControllerAdvice @ExceptionHandler -> ProblemDetail JSON
```

## Concepts
- **DispatcherServlet**: the front controller that receives every request (`EmbeddedTomcatServer`).
- **@RestController**, **@RequestMapping**, `@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping`.
- **Request data**: `@PathVariable` (`/tasks/{id}`), `@RequestParam` (`?done=true`), `@RequestBody` (JSON), `@RequestHeader`.
- **ResponseEntity**: control status codes and headers (201 + `Location`, 204).
- **Validation**: `@Valid @RequestBody` returns HTTP 400 automatically (lesson 12).
- **Global error handling**: `@RestControllerAdvice` + `@ExceptionHandler`, returning **ProblemDetail** (RFC 9457).
- **Interceptors** (`HandlerInterceptor`), **CORS** (`addCorsMappings`).
- **Request DTO vs response model**: `TaskRequest` vs `Task`, so clients cannot set server-owned fields like `id`.
- **REST conventions**: nouns in URLs (`/api/tasks`), HTTP verbs for actions, correct status codes
  (200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found, 409 Conflict, 500).
- Testing with **MockMvc**, without starting a server: see `TaskControllerTest` (lesson 20).

## Run
```
mvn -q compile exec:java -Dexec.args=19                 # port 8080
mvn -q compile exec:java -Dexec.args=19 -Dport=9090
```
Then use the `curl` commands the demo prints.

## From here to Spring Boot
Everything in `EmbeddedTomcatServer` and `WebConfig` is what `spring-boot-starter-web` auto-configures.
Continue with the `server_spring_boot` repository.
