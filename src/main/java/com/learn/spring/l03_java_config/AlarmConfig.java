package com.learn.spring.l03_java_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.spring.l01_without_spring.Alarm;
import com.learn.spring.l01_without_spring.SoundAlarm;
import com.learn.spring.l01_without_spring.VisualAlarm;

/** A second configuration class, pulled in with @Import from {@link DoorConfig}. */
@Configuration
public class AlarmConfig {

    // The method name becomes the bean name: "soundAlarm"
    @Bean
    public Alarm soundAlarm() {
        return new SoundAlarm();
    }

    @Bean
    public Alarm visualAlarm() {
        return new VisualAlarm();
    }
}
