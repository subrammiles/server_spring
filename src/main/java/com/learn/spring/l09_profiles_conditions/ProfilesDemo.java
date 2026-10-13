package com.learn.spring.l09_profiles_conditions;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProfilesDemo {

    public static void main(String[] args) {
        show(null, Map.of());
        show("dev", Map.of("features.recommendations", "true"));
        show("prod", Map.of("features.recommendations", "false"));
    }

    private static void show(String profile, Map<String, Object> props) {
        System.out.println("\n=== profile: " + (profile == null ? "(none -> 'default')" : profile) + ", props: " + props);
        try (AnnotationConfigApplicationContext ctx = ProfilesConfig.start(profile, props)) {
            System.out.println("storage         -> " + ctx.getBean(StorageService.class).store("report.pdf"));
            System.out.println("debug tools?    -> " + !ctx.getBeansOfType(DebugTools.class).isEmpty());
            RecommendationService recs = ctx.getBeanProvider(RecommendationService.class).getIfAvailable();
            System.out.println("recommendations -> "
                    + (recs == null ? "feature off, bean not registered" : recs.recommendFor("subbu")));
        }
    }
}
