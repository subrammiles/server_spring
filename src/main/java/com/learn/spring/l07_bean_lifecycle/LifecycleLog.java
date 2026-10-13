package com.learn.spring.l07_bean_lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Records lifecycle steps so the demo can print them and the test can check their order. */
public final class LifecycleLog {
    private static final List<String> STEPS = new CopyOnWriteArrayList<>();

    private LifecycleLog() {
    }

    public static void add(String step) {
        STEPS.add(step);
        System.out.println("  " + STEPS.size() + ". " + step);
    }

    public static List<String> steps() {
        return List.copyOf(STEPS);
    }

    public static void clear() {
        STEPS.clear();
    }
}
