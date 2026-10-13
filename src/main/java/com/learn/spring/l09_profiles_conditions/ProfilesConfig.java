package com.learn.spring.l09_profiles_conditions;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;

@Configuration
@ComponentScan
public class ProfilesConfig {

    /** Builds a container with the given profile and feature flags, the way a launcher would. */
    public static AnnotationConfigApplicationContext start(String profile, Map<String, Object> properties) {
        var context = new AnnotationConfigApplicationContext();
        if (profile != null) {
            context.getEnvironment().setActiveProfiles(profile);   // same as -Dspring.profiles.active=...
        }
        context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("demo", properties));
        context.register(ProfilesConfig.class);
        context.refresh();
        return context;
    }
}
