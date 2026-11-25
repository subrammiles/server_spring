package com.learn.spring.l08_externalized_config;

import java.math.BigDecimal;

/** A value type Spring knows nothing about. MoneyConverter teaches it to read "EUR 4.99". */
public record Money(String currency, BigDecimal amount) {

    @Override
    public String toString() {
        return currency + " " + amount;
    }
}
