package com.learn.spring.l04_component_scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** SETTER injection: useful for OPTIONAL dependencies. */
@Service
public class ReportService {
    private OrderRepository repository;

    @Autowired
    public void setRepository(OrderRepository repository) {
        this.repository = repository;
    }

    public String summary() {
        double total = repository.findAll().stream().mapToDouble(Order::amount).sum();
        return repository.findAll().size() + " orders, total = " + total;
    }
}
