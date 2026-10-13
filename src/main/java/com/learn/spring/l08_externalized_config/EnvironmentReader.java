package com.learn.spring.l08_externalized_config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** The Environment is the programmatic way to read properties (and profiles, lesson 09). */
@Component
public class EnvironmentReader {
    private final Environment env;

    public EnvironmentReader(Environment env) {
        this.env = env;
    }

    public String adminEmail() {
        return env.getRequiredProperty("app.admin.email");      // throws if missing
    }

    public Integer maxUsers() {
        return env.getProperty("app.max-users", Integer.class); // typed lookup
    }

    public String supportPhone() {
        return env.getProperty("app.support.phone", "not configured"); // with default
    }
}
