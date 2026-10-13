package com.learn.spring.l05_autowiring_choices;

/** Deliberately has NO implementation bean, to show optional injection. */
public interface FraudChecker {
    boolean isSuspicious(double amount);
}
