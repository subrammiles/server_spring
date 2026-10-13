package com.learn.spring.l06_bean_scopes;

import org.springframework.stereotype.Service;

/**
 * PITFALL: a singleton that injects a prototype directly.
 * Injection happens ONCE, when the singleton is created, so every customer shares one cart!
 */
@Service
public class BrokenCheckoutService {
    private final ShoppingCart cart;

    public BrokenCheckoutService(ShoppingCart cart) {
        this.cart = cart;
    }

    public ShoppingCart cartForNewCustomer() {
        return cart;
    }
}
