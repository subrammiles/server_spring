package com.learn.spring.l05_autowiring_choices;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Explicit bean name "paypal", used with @Qualifier("paypal"). */
@Component("paypal")
@Order(2)
public class PaypalGateway implements PaymentGateway {
    @Override
    public String pay(double amount) {
        return "PayPal charged " + amount;
    }
}
