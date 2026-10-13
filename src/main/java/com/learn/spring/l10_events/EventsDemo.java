package com.learn.spring.l10_events;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventsDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(EventsConfig.class)) {
            OrderService orders = context.getBean(OrderService.class);

            System.out.println("\n--- small order ---");
            orders.placeOrder("alice", 250);

            System.out.println("\n--- big order (triggers the conditional listener) ---");
            orders.placeOrder("bob", 5000);
            System.out.println();
        }
    }
}
