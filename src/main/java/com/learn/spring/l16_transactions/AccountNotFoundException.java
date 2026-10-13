package com.learn.spring.l16_transactions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String id) {
        super("No account " + id);
    }
}
