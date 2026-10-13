package com.learn.spring.l04_component_scan;

import java.util.List;

public interface OrderRepository {
    Order save(String product, double amount);

    List<Order> findAll();
}
