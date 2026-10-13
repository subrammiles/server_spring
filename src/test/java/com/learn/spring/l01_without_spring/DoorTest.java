package com.learn.spring.l01_without_spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** No Spring needed: because Door receives its Alarm, a test can hand it a fake one. */
class DoorTest {

    @Test
    void doorUsesWhateverAlarmItIsGiven() {
        Alarm fake = new Alarm() {
            public String activate() { return "fake on"; }
            public String deactivate() { return "fake off"; }
        };
        Door door = new Door(fake);

        assertThat(door.open()).isEqualTo("fake on");
        assertThat(door.close()).isEqualTo("fake off");
    }
}
