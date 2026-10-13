package com.learn.spring.l03_java_config;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learn.spring.l01_without_spring.Door;
import com.learn.spring.l01_without_spring.SoundAlarm;

public class JavaConfigDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(DoorConfig.class)) {
            System.out.println("Beans: " + Arrays.toString(context.getBeanDefinitionNames()));

            System.out.println("frontDoor  -> " + context.getBean("frontDoor", Door.class).open());
            System.out.println("garageDoor -> " + context.getBean("garageDoor", Door.class).open());

            // Inter-bean method calls return the same singleton thanks to the @Configuration proxy
            SoundAlarm shared = context.getBean("sharedAlarm", SoundAlarm.class);
            DoorConfig config = context.getBean(DoorConfig.class);
            System.out.println("Calling config.sharedAlarm() again returns the container's singleton: "
                    + (config.sharedAlarm() == shared));

            // The config class itself is a bean too, subclassed at runtime by CGLIB
            System.out.println("Config class at runtime: " + config.getClass().getSimpleName());
        }
    }
}
