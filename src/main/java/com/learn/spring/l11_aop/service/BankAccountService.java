package com.learn.spring.l11_aop.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.learn.spring.l11_aop.LogExecutionTime;

/**
 * Pure business logic. Notice: NO logging, timing or auditing code here.
 * All of that is added from the outside by aspects.
 */
@Service
public class BankAccountService {
    private final Map<String, Double> balances = new ConcurrentHashMap<>(Map.of("alice", 100.0, "bob", 50.0));

    public double deposit(String account, double amount) {
        return balances.merge(account, amount, Double::sum);
    }

    public double withdraw(String account, double amount) {
        double current = balances.getOrDefault(account, 0.0);
        if (current < amount) {
            throw new InsufficientFundsException(account + " has only " + current);
        }
        return balances.merge(account, -amount, Double::sum);
    }

    @LogExecutionTime
    public String monthlyStatement(String account) {
        sleep(30);
        return "Statement for " + account + ": balance " + balances.get(account);
    }

    /**
     * SELF-INVOCATION PITFALL: this.monthlyStatement() bypasses the proxy,
     * so TimingAspect does NOT run for that inner call.
     */
    public String yearlyReport(String account) {
        return "Yearly report -> " + this.monthlyStatement(account);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
