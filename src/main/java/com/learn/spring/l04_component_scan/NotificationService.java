package com.learn.spring.l04_component_scan;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/** @Component = "Spring, please create one of these and manage it". */
@Component
public class NotificationService {
    private final List<String> sent = new CopyOnWriteArrayList<>();

    public void notifyCustomer(String message) {
        sent.add(message);
        System.out.println("  [email] " + message);
    }

    public List<String> sentMessages() {
        return List.copyOf(sent);
    }
}
