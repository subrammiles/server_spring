package com.learn.spring.l04_component_scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FIELD injection: short, but AVOID it in new code.
 * - the field cannot be final
 * - you cannot create this class in a unit test without Spring (or reflection)
 * - dependencies are hidden instead of listed in the constructor
 */
@Component
public class LegacyAuditor {
    @Autowired
    private OrderRepository repository;

    public int orderCount() {
        return repository.findAll().size();
    }
}
