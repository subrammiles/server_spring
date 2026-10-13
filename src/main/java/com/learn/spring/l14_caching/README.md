# Lesson 14: The cache abstraction

## Concepts
- `@EnableCaching` plus a **CacheManager** bean turns the feature on.
- `@Cacheable("name")`: on a hit, return the cached value **without running the method**.
  The key defaults to the arguments. Customize it with SpEL: `key = "#keyword.toLowerCase()"`.
- `condition` / `unless`: decide whether to cache (`unless = "#result.isEmpty()"`).
- `@CachePut`: always run the method and store the result (refresh after an update).
- `@CacheEvict`: remove one entry, or `allEntries = true`.
- **Provider independent**: `ConcurrentMapCacheManager` (here), Caffeine, Redis, Hazelcast, JCache. Same annotations everywhere.

## Pitfalls
- Proxy based again: self-invocation skips the cache.
- Cache only data that tolerates being slightly stale, and plan invalidation. Stale caches are a classic bug.
- Cached objects are shared, so return immutable objects (records), or a caller may modify the cached copy.
- `ConcurrentMapCacheManager` never expires and grows without bound. It is for learning and tests only.

## Run
```
mvn -q compile exec:java -Dexec.args=14
```
