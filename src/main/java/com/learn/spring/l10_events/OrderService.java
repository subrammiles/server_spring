package com.learn.spring.l10_events;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * The publisher knows NOTHING about who reacts to an order (email, inventory, loyalty...).
 * New reactions are added by writing new listeners, without touching this class.
 */
@Service
public class OrderService {
    private final ApplicationEventPublisher publisher;
    private final AtomicLong ids = new AtomicLong(100);

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public long placeOrder(String customer, double amount) {
        long id = ids.incrementAndGet();
        System.out.println("Order " + id + " saved, publishing event...");
        publisher.publishEvent(new OrderPlacedEvent(id, customer, amount));
        System.out.println("publishEvent returned: listeners run synchronously by default");
        return id;
    }
}
