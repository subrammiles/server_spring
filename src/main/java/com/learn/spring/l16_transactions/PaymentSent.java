package com.learn.spring.l16_transactions;

import java.math.BigDecimal;

/** Published INSIDE a transaction whenever money moves. */
public record PaymentSent(String from, String to, BigDecimal amount) {

    @Override
    public String toString() {
        return amount + " " + from + " -> " + to;
    }
}
