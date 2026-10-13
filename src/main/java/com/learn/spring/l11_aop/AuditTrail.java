package com.learn.spring.l11_aop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/** Where the aspects write; lets the test assert what was intercepted. */
@Component
public class AuditTrail {
    private final List<String> entries = new CopyOnWriteArrayList<>();

    public void add(String entry) {
        entries.add(entry);
        System.out.println("    [aop] " + entry);
    }

    public List<String> entries() {
        return List.copyOf(entries);
    }

    public void clear() {
        entries.clear();
    }
}
