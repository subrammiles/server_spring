# Lesson 20: Testing Spring applications

Every lesson in this repo has tests (`src/test/java/com/learn/spring/lNN_*`). They check that the
examples really behave as their README claims, and they also serve as testing examples.
This folder collects the main techniques.

## The testing pyramid
```
        /\        few   end-to-end (real server + real DB)
       /  \             web slice: MockMvc (no server)
      /____\            integration: real container, maybe some mocks
     /______\     many  unit: plain Java + Mockito, no Spring
```

## Techniques and where to see them
| Technique | Key API | Example |
|---|---|---|
| Unit test without Spring | `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`, `when`, `verify` | `OrderServiceUnitTest` |
| Container-based test | `@SpringJUnitConfig(Config.class)` + `@Autowired` fields | most lesson tests |
| Replace one bean with a mock | `@MockitoBean` (Spring 6.2+, replaces `@MockBean`) | `MockitoBeanIntegrationTest` |
| Override properties | `@TestPropertySource(properties = "...")` | `ExternalizedConfigTest`, `AsyncSchedulingTest` |
| Activate profiles | `@ActiveProfiles("prod")` | `ProdProfileTest` |
| Roll back DB changes after each test | `@Transactional` on the test class | `EmployeeDaoTest`, `CustomerRepositoryTest` |
| Run SQL before a test | `@Sql(...)` | `SqlAndRollbackTest` |
| Code outside the test transaction | `@BeforeTransaction` / `@AfterTransaction` | `SqlAndRollbackTest` |
| Throw away a dirty context | `@DirtiesContext` | `LibraryServiceTest`, `EventsTest` |
| Assert published events | `@RecordApplicationEvents` + `ApplicationEvents` | `EventsTest` |
| Web layer without a server | `MockMvc` (+ Hamcrest `jsonPath`) | `TaskControllerTest` |
| Web layer, AssertJ style | `MockMvcTester` (Spring 6.2+) | `MockMvcTesterTest` |
| Extra test-only beans | nested `@Configuration` + `@Import` | `EmployeeDaoTest` |

## Context caching
Starting a container is slow, so Spring **caches contexts** and reuses one for every test class with the same
configuration. That makes suites fast, but state stored in singletons or in an in-memory database is
**shared between test classes** (see the note in `SqlAndRollbackTest`). Prefer rollback (`@Transactional`) or explicit
resets. Use `@DirtiesContext` only when necessary, because it forces a rebuild.

## Run
```
mvn test                                    # everything
mvn test -Dtest=TaskControllerTest          # one class
mvn test -Dtest='l16_transactions.*'        # one lesson
```
