package com.learn.spring.l01_without_spring;

/**
 * BAD: the door creates its own alarm with {@code new}.
 * To switch to a VisualAlarm, or to use a fake alarm in a test, you must edit this class.
 */
public class TightlyCoupledDoor {
    private final SoundAlarm alarm = new SoundAlarm();

    public String open() {
        return alarm.activate();
    }
}
