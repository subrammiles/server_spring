package com.learn.spring.l18_spring_data_jpa;

import org.springframework.data.jpa.domain.Specification;

/**
 * SPECIFICATIONS: small, reusable WHERE-clause pieces built with the JPA Criteria API,
 * combined at runtime with and() / or() / not().
 *
 * Derived queries and @Query are fixed at compile time. A search screen with 4 optional filters
 * would need 2^4 = 16 methods. With specifications it needs one: matching(filter).
 * The repository must extend JpaSpecificationExecutor<Customer>.
 *
 * (Strings like "city" are not checked by the compiler. The Hibernate JPA metamodel generator
 * creates a Customer_ class so you can write Customer_.city instead.)
 */
public final class CustomerSpecs {

    private CustomerSpecs() {
    }

    public static Specification<Customer> livesIn(String city) {
        return (root, query, cb) -> cb.equal(root.get("city"), city);
    }

    public static Specification<Customer> atLeast(int age) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("age"), age);
    }

    public static Specification<Customer> isActive(boolean active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }

    public static Specification<Customer> nameContains(String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + text.toLowerCase() + "%");
    }

    /** Adds a condition only for the filled-in fields. No filters = all customers. */
    public static Specification<Customer> matching(CustomerFilter filter) {
        Specification<Customer> spec = Specification.unrestricted();
        if (filter.nameContains() != null) {
            spec = spec.and(nameContains(filter.nameContains()));
        }
        if (filter.city() != null) {
            spec = spec.and(livesIn(filter.city()));
        }
        if (filter.minAge() != null) {
            spec = spec.and(atLeast(filter.minAge()));
        }
        if (filter.active() != null) {
            spec = spec.and(isActive(filter.active()));
        }
        return spec;
    }
}
