package com.learn.spring.l03_java_config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.learn.spring.l01_without_spring.Door;
import com.learn.spring.l01_without_spring.SoundAlarm;

/** @SpringJUnitConfig starts a container from the given config and injects beans into the test. */
@SpringJUnitConfig(DoorConfig.class)
class JavaConfigTest {

    @Autowired
    @Qualifier("garageDoor")   // an alias of backDoor
    Door garageDoor;

    @Autowired
    DoorConfig config;

    @Autowired
    SoundAlarm sharedAlarm;

    @Test
    void aliasResolvesToTheVisualDoor() {
        assertThat(garageDoor.open()).contains("Visual");
    }

    @Test
    void configurationProxyReturnsTheSingletonFromBeanMethods() {
        assertThat(config.sharedAlarm()).isSameAs(sharedAlarm);
    }
}
