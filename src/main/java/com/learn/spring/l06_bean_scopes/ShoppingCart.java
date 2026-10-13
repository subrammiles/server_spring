package com.learn.spring.l06_bean_scopes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** PROTOTYPE scope: a NEW cart every time one is requested from the container. */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCart {
    private static final AtomicInteger CREATED = new AtomicInteger();

    private final int number = CREATED.incrementAndGet();
    private final List<String> items = new ArrayList<>();

    public void add(String item) {
        items.add(item);
    }

    public List<String> items() {
        return List.copyOf(items);
    }

    public int number() {
        return number;
    }
}
