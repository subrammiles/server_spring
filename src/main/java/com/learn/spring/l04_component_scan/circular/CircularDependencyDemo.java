package com.learn.spring.l04_component_scan.circular;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learn.spring.l04_component_scan.circular.Cycles.Billing;
import com.learn.spring.l04_component_scan.circular.Cycles.BillingDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.LazyBillingDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.LazyOrderDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.OrderDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.Orders;
import com.learn.spring.l04_component_scan.circular.Cycles.TaxRules;

/** Registering classes directly: new AnnotationConfigApplicationContext(A.class, B.class) makes each a bean. */
public class CircularDependencyDemo {

    public static void main(String[] args) {
        System.out.println("1. Constructor cycle OrderDesk <-> BillingDesk");
        try (var context = new AnnotationConfigApplicationContext(OrderDesk.class, BillingDesk.class)) {
            System.out.println("   started?!");
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            System.out.println("   startup failed: " + root.getClass().getSimpleName());
        }

        System.out.println("\n2. @Lazy on one side");
        try (var context = new AnnotationConfigApplicationContext(LazyOrderDesk.class, LazyBillingDesk.class)) {
            LazyOrderDesk orders = context.getBean(LazyOrderDesk.class);
            System.out.println("   " + orders.place("Keyboard"));
            System.out.println("   injected: " + orders.injectedBillingClass().getSimpleName());
        }

        System.out.println("\n3. Redesign: both depend on TaxRules");
        try (var context = new AnnotationConfigApplicationContext(TaxRules.class, Orders.class, Billing.class)) {
            System.out.println("   " + context.getBean(Orders.class).place("Monitor", 100));
            System.out.println("   " + context.getBean(Billing.class).invoice("Monitor", 100));
        }
    }
}
