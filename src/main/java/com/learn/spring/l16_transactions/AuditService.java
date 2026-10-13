package com.learn.spring.l16_transactions;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private final JdbcClient jdbc;

    public AuditService(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * REQUIRES_NEW suspends the caller's transaction and runs in its OWN one, which commits
     * immediately. The audit entry survives even if the transfer that called it rolls back.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String message) {
        jdbc.sql("INSERT INTO audit_log (message) VALUES (?)").param(message).update();
    }

    public List<String> entries() {
        return jdbc.sql("SELECT message FROM audit_log ORDER BY id").query(String.class).list();
    }
}
