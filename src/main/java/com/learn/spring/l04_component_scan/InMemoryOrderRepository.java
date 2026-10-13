package com.learn.spring.l04_component_scan;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

/** @Repository = a @Component for the data-access layer (it also enables exception translation). */
@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders = new CopyOnWriteArrayList<>();
    private final AtomicLong ids = new AtomicLong();

    @Override
    public Order save(String product, double amount) {
        Order order = new Order(ids.incrementAndGet(), product, amount);
        orders.add(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(orders);
    }
}
