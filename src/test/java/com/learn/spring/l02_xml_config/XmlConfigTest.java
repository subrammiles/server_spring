package com.learn.spring.l02_xml_config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learn.spring.l01_without_spring.Door;

class XmlConfigTest {

    @Test
    void xmlDefinesAndWiresTheBeans() {
        try (var context = new ClassPathXmlApplicationContext("l02/beans.xml")) {
            assertThat(context.getBean("frontDoor", Door.class).open()).contains("Sound");
            assertThat(context.getBean("backDoor", Door.class).open()).contains("Visual");

            SecuritySystem system = context.getBean(SecuritySystem.class);
            assertThat(system.getName()).isEqualTo("Home Security v1");
            assertThat(system.triggerAll()).hasSize(2);

            assertThat(context.getBean("mainEntrance")).isSameAs(context.getBean("frontDoor"));
        }
    }
}
