package com.learn.spring.l04_component_scan;

import org.springframework.stereotype.Service;

/**
 * CONSTRUCTOR injection (recommended).
 * With a single constructor, @Autowired is optional: Spring uses it automatically.
 */
@Service
public class OrderService {
    private final OrderRepository repository;
    private final NotificationService notifications;

    public OrderService(OrderRepository repository, NotificationService notifications) {
        this.repository = repository;
        this.notifications = notifications;
    }

    public Order placeOrder(String product, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        Order order = repository.save(product, amount);
        notifications.notifyCustomer("Order #" + order.id() + " for " + product + " confirmed");
        return order;
    }
}
