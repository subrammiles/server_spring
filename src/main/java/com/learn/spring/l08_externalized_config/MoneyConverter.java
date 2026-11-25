package com.learn.spring.l08_externalized_config;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

/**
 * A Converter<S, T> is a one-way, stateless conversion. Register it with the ConversionService
 * (see ConfigConfig) and every @Value, Environment.getProperty(key, Money.class), web request
 * parameter and SpEL expression can produce a Money from a String.
 */
public class MoneyConverter implements Converter<String, Money> {

    @Override
    public Money convert(String source) {
        String[] parts = source.trim().split("\\s+");
        try {
            if (parts.length == 2) {
                return new Money(parts[0].toUpperCase(), new BigDecimal(parts[1]));
            }
        } catch (NumberFormatException e) {
            // fall through to the clear error below
        }
        throw new IllegalArgumentException("Expected '<CURRENCY> <amount>', got '" + source + "'");
    }
}
