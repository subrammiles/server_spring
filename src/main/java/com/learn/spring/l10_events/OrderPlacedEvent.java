package com.learn.spring.l10_events;

/** Since Spring 4.2 any object can be an event; no need to extend ApplicationEvent. */
public record OrderPlacedEvent(long orderId, String customer, double amount) {
}
