package com.learn.spring.l05_autowiring_choices;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Experimental
@Order(3)
public class CryptoGateway implements PaymentGateway {
    @Override
    public String pay(double amount) {
        return "Crypto wallet charged " + amount;
    }
}
