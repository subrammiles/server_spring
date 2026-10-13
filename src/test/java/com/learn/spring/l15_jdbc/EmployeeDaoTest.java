package com.learn.spring.l15_jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Transactional on a TEST class: every test runs in a transaction that is ROLLED BACK at the
 * end, so tests can insert/delete freely without affecting each other.
 * JdbcConfig has no transaction manager, so the test adds one through a nested config.
 */
@SpringJUnitConfig
@Transactional
class EmployeeDaoTest {

    @Configuration
    @Import(JdbcConfig.class)
    static class TestConfig {
        @Bean
        PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Autowired
    EmployeeDao dao;

    @Test
    void readsSeedData() {
        assertThat(dao.count()).isEqualTo(4);
        assertThat(dao.findAll()).extracting(Employee::name).containsExactly("Asha", "Ravi", "Meera", "Kiran");
        assertThat(dao.findById(3)).get().extracting(Employee::department).isEqualTo("SALES");
        assertThat(dao.findById(999)).isEmpty();
    }

    @Test
    void insertReturnsGeneratedIdAndIsRolledBackAfterTheTest() {
        Employee saved = dao.insert(Employee.newEmployee("Test", "QA", new BigDecimal("50000"), "test@corp.com"));

        assertThat(saved.id()).isNotNull();
        assertThat(dao.count()).isEqualTo(5);
    }

    @Test
    void namedParametersBatchAndAggregates() {
        assertThat(dao.findByDepartmentEarningAtLeast("ENGINEERING", new BigDecimal("110000")))
                .extracting(Employee::name).containsExactly("Asha");
        assertThat(dao.batchInsert(List.of(
                Employee.newEmployee("A", "HR", BigDecimal.TEN, null),
                Employee.newEmployee("B", "HR", BigDecimal.TEN, null)))).isEqualTo(2);
        assertThat(dao.averageSalaryByDepartment()).containsOnlyKeys("ENGINEERING", "HR", "SALES");
    }

    @Test
    void updatesAndDeletes() {
        assertThat(dao.raiseSalaries("ENGINEERING", 10)).isEqualTo(2);
        assertThat(dao.getById(1).salary()).isEqualByComparingTo("132000");
        assertThat(dao.deleteById(4)).isTrue();
        assertThat(dao.deleteById(4)).isFalse();
    }

    @Test
    void sqlErrorsAreTranslated() {
        assertThatThrownBy(() -> dao.insert(Employee.newEmployee("X", "HR", BigDecimal.ONE, "asha@corp.com")))
                .isInstanceOf(DuplicateKeyException.class);
        assertThatThrownBy(() -> dao.getById(999)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
