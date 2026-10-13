package com.learn.spring.l02_xml_config;

import java.util.Arrays;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learn.spring.l01_without_spring.Door;

public class XmlConfigDemo {

    public static void main(String[] args) {
        // The ApplicationContext IS the IoC container. It reads the XML, creates every bean
        // and wires them together. try-with-resources closes it (and destroys the beans).
        try (var context = new ClassPathXmlApplicationContext("l02/beans.xml")) {

            System.out.println("Beans in the container: " + Arrays.toString(context.getBeanDefinitionNames()));

            // Look up by name + type (type-safe, no cast needed)
            Door frontDoor = context.getBean("frontDoor", Door.class);
            Door backDoor = context.getBean("backDoor", Door.class);
            System.out.println("frontDoor.open() -> " + frontDoor.open());
            System.out.println("backDoor.open()  -> " + backDoor.open());

            // Look up by type only: works when exactly one bean has that type
            SecuritySystem system = context.getBean(SecuritySystem.class);
            System.out.println(system.getName() + " -> " + system.triggerAll());

            // Beans are singletons by default: same name -> same object
            Door alias = context.getBean("mainEntrance", Door.class);
            System.out.println("alias 'mainEntrance' is the same object as frontDoor? " + (alias == frontDoor));
        }
    }
}
