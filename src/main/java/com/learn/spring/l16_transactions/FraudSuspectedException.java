package com.learn.spring.l16_transactions;

/** A CHECKED exception: by default it does NOT trigger a rollback. */
public class FraudSuspectedException extends Exception {
    public FraudSuspectedException(String message) {
        super(message);
    }
}
