package com.learn.spring.l16_transactions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * PROGRAMMATIC transactions with TransactionTemplate: useful when only PART of a method
 * should be transactional, or when you want to decide in code whether to roll back.
 */
@Service
public class BulkPaymentService {
    private final TransactionTemplate tx;
    private final AccountRepository accounts;

    public BulkPaymentService(TransactionTemplate tx, AccountRepository accounts) {
        this.tx = tx;
        this.accounts = accounts;
    }

    /** Pays everyone or no one. Returns true if committed. */
    public boolean payAll(String from, Map<String, BigDecimal> payments, BigDecimal maxTotal) {
        return Boolean.TRUE.equals(tx.execute(status -> {
            BigDecimal total = BigDecimal.ZERO;
            for (var payment : payments.entrySet()) {
                accounts.adjust(from, payment.getValue().negate());
                accounts.adjust(payment.getKey(), payment.getValue());
                total = total.add(payment.getValue());
            }
            if (total.compareTo(maxTotal) > 0) {
                status.setRollbackOnly();   // roll back without throwing an exception
                return false;
            }
            return true;
        }));
    }
}
