package com.learn.spring.l14_caching;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CachingConfig.class)
class CachingTest {

    @Autowired
    ProductCatalog catalog;

    @BeforeEach
    void emptyCaches() {
        catalog.clearAll();
    }

    @Test
    void secondLookupIsServedFromTheCache() {
        int before = catalog.databaseCalls();
        catalog.findById(2);
        catalog.findById(2);
        assertThat(catalog.databaseCalls() - before).isEqualTo(1);
    }

    @Test
    void cachePutRefreshesAndCacheEvictForcesReload() {
        catalog.findById(3);
        int before = catalog.databaseCalls();

        assertThat(catalog.updatePrice(3, 39.0).price()).isEqualTo(39.0);
        assertThat(catalog.findById(3).price()).isEqualTo(39.0);
        assertThat(catalog.databaseCalls() - before).isZero();

        catalog.evict(3);
        catalog.findById(3);
        assertThat(catalog.databaseCalls() - before).isEqualTo(1);
    }

    @Test
    void emptySearchResultsAreNotCached() {
        int before = catalog.databaseCalls();
        catalog.search("phone");
        catalog.search("phone");
        assertThat(catalog.databaseCalls() - before).isEqualTo(2);
    }
}
