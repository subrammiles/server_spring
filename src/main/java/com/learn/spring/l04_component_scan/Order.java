package com.learn.spring.l04_component_scan;

/** A plain data object. NOT a bean: Spring manages services, not every object you create. */
public record Order(long id, String product, double amount) {
}
