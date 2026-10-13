package com.learn.spring.l14_caching;

import java.util.function.Supplier;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CachingDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(CachingConfig.class)) {
            ProductCatalog catalog = context.getBean(ProductCatalog.class);

            timed("findById(1) first call ", () -> catalog.findById(1));
            timed("findById(1) second call", () -> catalog.findById(1));
            timed("search(\"LAPTOP\")       ", () -> catalog.search("LAPTOP"));
            timed("search(\"laptop\") (same key after toLowerCase)", () -> catalog.search("laptop"));

            catalog.updatePrice(1, 899.0);
            timed("findById(1) after @CachePut", () -> catalog.findById(1));

            catalog.evict(1);
            timed("findById(1) after @CacheEvict", () -> catalog.findById(1));

            System.out.println("\nreal database calls: " + catalog.databaseCalls());
        }
    }

    private static void timed(String label, Supplier<Object> call) {
        long start = System.nanoTime();
        Object result = call.get();
        System.out.printf("%-48s %4d ms  %s%n", label, (System.nanoTime() - start) / 1_000_000, result);
    }
}
