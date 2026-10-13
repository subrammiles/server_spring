package com.learn.spring.l20_testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.learn.spring.l16_transactions.AccountRepository;
import com.learn.spring.l16_transactions.TxConfig;

/**
 * TECHNIQUE 3: database tests that clean up after themselves.
 * - @Sql runs SQL before a test (inside the test transaction).
 * - @Transactional on a test rolls everything back afterwards (@Commit would keep it).
 * - @BeforeTransaction runs BEFORE the test transaction starts, i.e. sees committed data.
 *
 * Context caching: Spring reuses the ApplicationContext (and so this in-memory database)
 * across test classes with the same configuration. TransferServiceTest uses TxConfig too,
 * so the balances here may be whatever it left behind. That is why we record the starting
 * balance instead of assuming 500.
 */
@SpringJUnitConfig(TxConfig.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SqlAndRollbackTest {

    private static BigDecimal committedBalance;

    @Autowired
    AccountRepository accounts;

    @BeforeTransaction
    void rememberCommittedBalance() {
        if (committedBalance == null) {
            committedBalance = accounts.balanceOf("ACC-2");
        }
    }

    @Test
    @Order(1)
    @Sql(statements = "UPDATE account SET balance = 0 WHERE id = 'ACC-2'")
    void sqlScriptPreparesTheData() {
        assertThat(accounts.balanceOf("ACC-2")).isZero();
    }

    @Test
    @Order(2)
    void previousTestWasRolledBack() {
        assertThat(accounts.balanceOf("ACC-2")).isEqualByComparingTo(committedBalance);
    }
}
