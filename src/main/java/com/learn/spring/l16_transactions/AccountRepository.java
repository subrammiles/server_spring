package com.learn.spring.l16_transactions;

import java.math.BigDecimal;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    private final JdbcClient jdbc;

    public AccountRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public BigDecimal balanceOf(String id) {
        return jdbc.sql("SELECT balance FROM account WHERE id = ?").param(id)
                .query(BigDecimal.class).optional()
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public void adjust(String id, BigDecimal delta) {
        int rows = jdbc.sql("UPDATE account SET balance = balance + ? WHERE id = ?").params(delta, id).update();
        if (rows == 0) {
            throw new AccountNotFoundException(id);
        }
    }

    public BigDecimal total() {
        return jdbc.sql("SELECT SUM(balance) FROM account").query(BigDecimal.class).single();
    }

    public void reset() {
        jdbc.sql("UPDATE account SET balance = 1000 WHERE id = 'ACC-1'").update();
        jdbc.sql("UPDATE account SET balance = 500 WHERE id = 'ACC-2'").update();
        jdbc.sql("DELETE FROM audit_log").update();
    }
}
