package com.learn.spring.l08_externalized_config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * ${...} = property placeholder (looked up in the Environment)
 * #{...} = SpEL expression (evaluated, can call methods, do maths, reference other beans)
 */
@Component
public class AppSettings {
    private final String name;
    private final int maxUsers;
    private final String region;
    private final String[] features;
    private final int maxSessions;
    private final String javaVersion;
    private final int discountPercent;
    private final Resource welcomeFile;

    public AppSettings(@Value("${app.name}") String name,
                       // Strings are converted to int automatically
                       @Value("${app.max-users}") int maxUsers,
                       // Default value after the colon, used when the property is missing
                       @Value("${app.region:us-east-1}") String region,
                       // A comma-separated value converts to an array
                       @Value("${app.features}") String[] features,
                       // SpEL arithmetic on top of a placeholder
                       @Value("#{${app.max-users} * 2}") int maxSessions,
                       // SpEL reading a JVM system property
                       @Value("#{systemProperties['java.version']}") String javaVersion,
                       @Value("${app.discount-percent}") int discountPercent,
                       // Strings are also converted to Resources (classpath:, file:, https:)
                       @Value("classpath:l08/welcome.txt") Resource welcomeFile) {
        this.name = name;
        this.maxUsers = maxUsers;
        this.region = region;
        this.features = features;
        this.maxSessions = maxSessions;
        this.javaVersion = javaVersion;
        this.discountPercent = discountPercent;
        this.welcomeFile = welcomeFile;
    }

    public String getName() { return name; }
    public int getMaxUsers() { return maxUsers; }
    public String getRegion() { return region; }
    public String[] getFeatures() { return features.clone(); }
    public int getMaxSessions() { return maxSessions; }
    public String getJavaVersion() { return javaVersion; }
    public int getDiscountPercent() { return discountPercent; }

    public String welcomeText() {
        try {
            return welcomeFile.getContentAsString(StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + welcomeFile, e);
        }
    }
}
