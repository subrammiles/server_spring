package com.learn.spring.l01_without_spring;

public class VisualAlarm implements Alarm {
    @Override
    public String activate() {
        return "Visual alarm: lights flashing";
    }

    @Override
    public String deactivate() {
        return "Visual alarm: lights off";
    }
}
