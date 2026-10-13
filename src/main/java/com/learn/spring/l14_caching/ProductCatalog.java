package com.learn.spring.l14_caching;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/** Pretends to be a slow database (200 ms per call) and counts how often it is really hit. */
@Service
public class ProductCatalog {
    private final Map<Long, Product> table = new ConcurrentHashMap<>(Map.of(
            1L, new Product(1, "Laptop", 999.0),
            2L, new Product(2, "Mouse", 19.0),
            3L, new Product(3, "Laptop bag", 49.0)));
    private final AtomicInteger databaseCalls = new AtomicInteger();

    /** First call per id hits the "database"; later calls with the same id come from the cache. */
    @Cacheable("products")
    public Product findById(long id) {
        slowDatabase();
        return table.get(id);
    }

    /** Custom key (SpEL) and "don't cache empty results". */
    @Cacheable(cacheNames = "searches", key = "#keyword.toLowerCase()", unless = "#result.isEmpty()")
    public List<Product> search(String keyword) {
        slowDatabase();
        return table.values().stream()
                .filter(p -> p.name().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    /** @CachePut ALWAYS runs the method, then stores the result: keeps the cache fresh after updates. */
    @CachePut(cacheNames = "products", key = "#id")
    public Product updatePrice(long id, double newPrice) {
        Product updated = new Product(id, table.get(id).name(), newPrice);
        table.put(id, updated);
        return updated;
    }

    /** Removes one entry; the next findById(id) goes to the database again. */
    @CacheEvict(cacheNames = "products", key = "#id")
    public void evict(long id) {
    }

    @CacheEvict(cacheNames = {"products", "searches"}, allEntries = true)
    public void clearAll() {
    }

    public int databaseCalls() {
        return databaseCalls.get();
    }

    private void slowDatabase() {
        databaseCalls.incrementAndGet();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
