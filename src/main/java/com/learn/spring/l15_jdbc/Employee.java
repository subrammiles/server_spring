package com.learn.spring.l15_jdbc;

import java.math.BigDecimal;

/** Maps one row of the employee table. id is null until the database generates it. */
public record Employee(Long id, String name, String department, BigDecimal salary, String email) {

    public static Employee newEmployee(String name, String department, BigDecimal salary, String email) {
        return new Employee(null, name, department, salary, email);
    }
}
