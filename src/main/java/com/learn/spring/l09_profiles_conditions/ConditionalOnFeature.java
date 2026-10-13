package com.learn.spring.l09_profiles_conditions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * Our own conditional annotation: register the bean only when the property
 * "features.<value>" is "true". Spring Boot's @ConditionalOnProperty works the same way.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(OnFeatureCondition.class)
public @interface ConditionalOnFeature {
    String value();
}
