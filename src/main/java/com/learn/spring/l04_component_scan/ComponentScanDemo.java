package com.learn.spring.l04_component_scan;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Default bean name = class name with a lower-case first letter
            System.out.println("Scanned beans: " + Arrays.toString(context.getBeanDefinitionNames()));

            OrderService orders = context.getBean(OrderService.class);
            orders.placeOrder("Keyboard", 49.99);
            orders.placeOrder("Monitor", 199.00);

            System.out.println("Report  : " + context.getBean(ReportService.class).summary());
            System.out.println("Auditor : " + context.getBean(LegacyAuditor.class).orderCount() + " orders seen");
        }
    }
}
