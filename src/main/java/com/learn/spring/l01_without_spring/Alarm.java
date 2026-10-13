package com.learn.spring.l01_without_spring;

/** The abstraction a Door depends on. Any alarm type can be plugged in. */
public interface Alarm {
    String activate();

    String deactivate();
}
