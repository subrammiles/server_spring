package com.learn.spring.l10_events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/** Collects what the listeners did, for printing and for tests. */
@Component
public class EventLog {
    private final List<String> entries = new CopyOnWriteArrayList<>();

    public void add(String entry) {
        entries.add(entry);
        System.out.println("  " + entry);
    }

    public List<String> entries() {
        return List.copyOf(entries);
    }
}
