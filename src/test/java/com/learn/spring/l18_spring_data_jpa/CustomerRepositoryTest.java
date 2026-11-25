package com.learn.spring.l18_spring_data_jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

/** Each test (including the @BeforeEach data) runs in a transaction rolled back afterwards. */
@SpringJUnitConfig(DataJpaConfig.class)
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repo;

    @BeforeEach
    void seed() {
        repo.saveAll(List.of(
                new Customer("Anil", "anil@mail.com", "Hyderabad", 34),
                new Customer("Bhavana", "bhavana@mail.com", "Bengaluru", 27),
                new Customer("Chetan", "chetan@mail.com", "Hyderabad", 45),
                new Customer("Deepa", "deepa@mail.com", "Chennai", 19)));
    }

    @Test
    void derivedQueries() {
        assertThat(repo.findByCity("Hyderabad")).extracting(Customer::getName).containsExactlyInAnyOrder("Anil", "Chetan");
        assertThat(repo.findByEmailIgnoreCase("ANIL@MAIL.COM")).isPresent();
        assertThat(repo.findByAgeBetweenOrderByAgeAsc(20, 40)).extracting(Customer::getName).containsExactly("Bhavana", "Anil");
        assertThat(repo.findByNameStartingWith("Ch")).hasSize(1);
        assertThat(repo.existsByEmail("deepa@mail.com")).isTrue();
    }

    @Test
    void paging() {
        Page<Customer> first = repo.findByActiveTrue(PageRequest.of(0, 3, Sort.by("name")));

        assertThat(first.getTotalElements()).isEqualTo(4);
        assertThat(first.getTotalPages()).isEqualTo(2);
        assertThat(first.getContent()).extracting(Customer::getName).containsExactly("Anil", "Bhavana", "Chetan");
    }

    @Test
    void projectionsAndQueries() {
        assertThat(repo.findByCityOrderByName("Hyderabad")).extracting(CustomerSummary::getName).containsExactly("Anil", "Chetan");
        assertThat(repo.countPerCity()).contains(new CityCount("Hyderabad", 2));
        assertThat(repo.findActiveAtLeast(30)).extracting(Customer::getName).containsExactly("Anil", "Chetan");
        assertThat(repo.findByCityNative("Chennai")).extracting(Customer::getName).containsExactly("Deepa");
    }

    @Test
    void bulkUpdate() {
        assertThat(repo.deactivateAllIn("Hyderabad")).isEqualTo(2);
        assertThat(repo.countByActiveTrue()).isEqualTo(2);
    }

    @Test
    void auditingFillsTimestamps() {
        Customer anil = repo.findByEmailIgnoreCase("anil@mail.com").orElseThrow();
        assertThat(anil.getCreatedAt()).isNotNull();
        assertThat(anil.getUpdatedAt()).isNotNull();
    }

    @Test
    void specificationsBuildQueriesFromOptionalFilters() {
        assertThat(repo.findAll(CustomerSpecs.matching(new CustomerFilter(null, null, null, null)))).hasSize(4);
        assertThat(repo.findAll(CustomerSpecs.matching(new CustomerFilter(null, "Hyderabad", 40, null))))
                .extracting(Customer::getName).containsExactly("Chetan");
        assertThat(repo.findAll(CustomerSpecs.matching(new CustomerFilter("AN", null, null, true)), Sort.by("name")))
                .extracting(Customer::getName).containsExactly("Anil", "Bhavana", "Chetan");

        Page<Customer> page = repo.findAll(CustomerSpecs.livesIn("Chennai").or(CustomerSpecs.atLeast(40)),
                PageRequest.of(0, 1, Sort.by("name")));
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).extracting(Customer::getName).containsExactly("Chetan");
    }

    @Test
    void queryByExampleMatchesTheFilledInFields() {
        Customer probe = new Customer("an", null, "Hyderabad", 0);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age", "active")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        assertThat(repo.findAll(Example.of(probe, matcher), Sort.by("name")))
                .extracting(Customer::getName).containsExactly("Anil", "Chetan");
    }
}
