package com.learn.spring.l03_java_config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.learn.spring.l01_without_spring.Alarm;
import com.learn.spring.l01_without_spring.Door;
import com.learn.spring.l01_without_spring.SoundAlarm;

/**
 * Java-based configuration: the same "recipes" as lesson 02's XML, written as plain Java.
 * The compiler now checks class names and constructor arguments for you.
 */
@Configuration
@Import(AlarmConfig.class)
public class DoorConfig {

    /** Style 1: declare the dependency as a method parameter. Spring passes the bean in. */
    @Bean
    public Door frontDoor(@Qualifier("soundAlarm") Alarm alarm) {
        return new Door(alarm);
    }

    /** Several names (aliases) for one bean. */
    @Bean(name = {"backDoor", "garageDoor"})
    public Door backDoor(@Qualifier("visualAlarm") Alarm alarm) {
        return new Door(alarm);
    }

    /**
     * Style 2: call another @Bean method. Because @Configuration classes are proxied (CGLIB),
     * calling sharedAlarm() twice returns the SAME singleton instead of creating two objects.
     */
    @Bean
    public SoundAlarm sharedAlarm() {
        return new SoundAlarm();
    }

    @Bean
    public Door sideDoor() {
        return new Door(sharedAlarm());
    }

    @Bean
    public Door cellarDoor() {
        return new Door(sharedAlarm());
    }
}
