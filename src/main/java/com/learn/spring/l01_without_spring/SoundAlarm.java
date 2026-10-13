package com.learn.spring.l01_without_spring;

public class SoundAlarm implements Alarm {
    @Override
    public String activate() {
        return "Sound alarm: BEEP BEEP";
    }

    @Override
    public String deactivate() {
        return "Sound alarm: silent";
    }
}
