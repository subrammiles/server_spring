package com.learn.spring.l01_without_spring;

/**
 * GOOD: the door depends on the {@link Alarm} interface and receives it from outside
 * (constructor injection). The door no longer decides which alarm it gets.
 */
public class Door {
    private final Alarm alarm;

    public Door(Alarm alarm) {
        this.alarm = alarm;
    }

    public String open() {
        return alarm.activate();
    }

    public String close() {
        return alarm.deactivate();
    }
}
