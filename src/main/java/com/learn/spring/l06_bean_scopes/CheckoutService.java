package com.learn.spring.l06_bean_scopes;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/** FIX: inject an ObjectProvider and ask it for a fresh prototype each time. */
@Service
public class CheckoutService {
    private final ObjectProvider<ShoppingCart> carts;

    public CheckoutService(ObjectProvider<ShoppingCart> carts) {
        this.carts = carts;
    }

    public ShoppingCart cartForNewCustomer() {
        return carts.getObject();
    }
}
