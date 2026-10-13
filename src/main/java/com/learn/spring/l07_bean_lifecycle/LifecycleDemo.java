package com.learn.spring.l07_bean_lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifecycleDemo {

    public static void main(String[] args) {
        LifecycleLog.clear();
        System.out.println("--- starting the container ---");
        var context = new AnnotationConfigApplicationContext(LifecycleConfig.class);

        System.out.println("--- bean in use: pool open = " + context.getBean(ConnectionPool.class).isOpen() + " ---");

        System.out.println("--- closing the container ---");
        context.close();
    }
}
