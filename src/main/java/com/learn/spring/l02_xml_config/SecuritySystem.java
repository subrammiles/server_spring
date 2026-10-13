package com.learn.spring.l02_xml_config;

import java.util.ArrayList;
import java.util.List;

import com.learn.spring.l01_without_spring.Alarm;

/**
 * A bean configured with SETTER injection: Spring calls the no-arg constructor,
 * then {@code setName(...)} and {@code setAlarms(...)}.
 */
public class SecuritySystem {
    private String name;
    private List<Alarm> alarms = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public String getName() {
        return name;
    }

    public List<String> triggerAll() {
        return alarms.stream().map(Alarm::activate).toList();
    }
}
