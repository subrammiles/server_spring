package com.learn.spring.l09_profiles_conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnFeatureCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var attributes = metadata.getAnnotationAttributes(ConditionalOnFeature.class.getName());
        String feature = (String) attributes.get("value");
        return context.getEnvironment().getProperty("features." + feature, Boolean.class, false);
    }
}
