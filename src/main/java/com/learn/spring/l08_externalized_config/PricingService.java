package com.learn.spring.l08_externalized_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
    private final double discountRate;

    /** SpEL can reference OTHER beans by name: here the appSettings bean's getter. */
    public PricingService(@Value("#{appSettings.discountPercent / 100.0}") double discountRate) {
        this.discountRate = discountRate;
    }

    public double priceAfterDiscount(double price) {
        return price * (1 - discountRate);
    }
}
