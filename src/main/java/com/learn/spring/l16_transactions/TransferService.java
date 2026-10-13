package com.learn.spring.l16_transactions;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final AccountRepository accounts;
    private final AuditService audit;

    public TransferService(AccountRepository accounts, AuditService audit) {
        this.accounts = accounts;
        this.audit = audit;
    }

    /** NO transaction: each UPDATE commits on its own. If the credit fails, the money is gone! */
    public void transferWithoutTransaction(String from, String to, BigDecimal amount) {
        debit(from, amount);
        accounts.adjust(to, amount);
    }

    /**
     * ALL OR NOTHING: both updates commit together, or a RuntimeException rolls both back.
     * The audit entry is written in its own transaction (REQUIRES_NEW) and survives a rollback.
     */
    @Transactional
    public void transfer(String from, String to, BigDecimal amount) {
        audit.log("transfer " + amount + " " + from + " -> " + to);
        debit(from, amount);
        accounts.adjust(to, amount);
    }

    /** SURPRISE: a CHECKED exception does not roll back by default. The debit is committed! */
    @Transactional
    public void transferWithFraudCheck(String from, String to, BigDecimal amount) throws FraudSuspectedException {
        transferWithFraudCheckBody(from, to, amount);
    }

    /** Fix: tell Spring which checked exceptions should roll back. */
    @Transactional(rollbackFor = FraudSuspectedException.class)
    public void safeTransferWithFraudCheck(String from, String to, BigDecimal amount) throws FraudSuspectedException {
        transferWithFraudCheckBody(from, to, amount);
    }

    /** readOnly = a hint that lets the database/driver optimise; no writes expected. */
    @Transactional(readOnly = true)
    public BigDecimal totalMoneyInBank() {
        return accounts.total();
    }

    private void transferWithFraudCheckBody(String from, String to, BigDecimal amount) throws FraudSuspectedException {
        debit(from, amount);
        if (amount.compareTo(new BigDecimal("400")) > 0) {
            throw new FraudSuspectedException("amount too high: " + amount);
        }
        accounts.adjust(to, amount);
    }

    private void debit(String from, BigDecimal amount) {
        if (accounts.balanceOf(from).compareTo(amount) < 0) {
            throw new InsufficientFundsException(from + " cannot pay " + amount);
        }
        accounts.adjust(from, amount.negate());
    }
}
