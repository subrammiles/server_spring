package com.learn.spring.l06_bean_scopes;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopesDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ScopesConfig.class)) {
            System.out.println("--- singleton ---");
            VisitCounter a = context.getBean(VisitCounter.class);
            VisitCounter b = context.getBean(VisitCounter.class);
            a.visit();
            System.out.println("same instance? " + (a == b) + ", visits seen through b: " + b.visit());

            System.out.println("\n--- prototype ---");
            ShoppingCart c1 = context.getBean(ShoppingCart.class);
            ShoppingCart c2 = context.getBean(ShoppingCart.class);
            System.out.println("cart #" + c1.number() + " vs cart #" + c2.number() + ", same? " + (c1 == c2));

            System.out.println("\n--- prototype injected into singleton (the pitfall) ---");
            BrokenCheckoutService broken = context.getBean(BrokenCheckoutService.class);
            broken.cartForNewCustomer().add("alice's book");
            System.out.println("bob's cart already contains: " + broken.cartForNewCustomer().items());

            System.out.println("\n--- the fix: ObjectProvider ---");
            CheckoutService fixed = context.getBean(CheckoutService.class);
            fixed.cartForNewCustomer().add("alice's book");
            System.out.println("bob's cart contains: " + fixed.cartForNewCustomer().items());

            System.out.println("\n--- @Lazy ---");
            System.out.println("created after startup? " + ExpensiveReportEngine.created);
            System.out.println(context.getBean(ExpensiveReportEngine.class).run());
        }
    }
}
