package com.learn.spring.l05_autowiring_choices;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiringDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AutowiringConfig.class)) {
            CheckoutService checkout = context.getBean(CheckoutService.class);

            System.out.println("@Primary            -> " + checkout.checkout(10));
            System.out.println("@Qualifier(\"paypal\") -> " + checkout.payWithPaypal(20));
            System.out.println("@Experimental       -> " + checkout.payWithExperimental(30));
            System.out.println("List (by @Order)    -> " + checkout.gatewayClassesInOrder());
            System.out.println("Map lookup 'paypal' -> " + checkout.payWith("paypal", 40));
            System.out.println("No FraudChecker bean exists, checkout still works -> " + checkout.checkout(50));
        }
    }
}
