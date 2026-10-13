package com.learn.spring.l05_autowiring_choices;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** @Primary: the winner when several beans match and nothing else decides. */
@Component
@Primary
@Order(1)
public class StripeGateway implements PaymentGateway {
    @Override
    public String pay(double amount) {
        return "Stripe charged " + amount;
    }
}
