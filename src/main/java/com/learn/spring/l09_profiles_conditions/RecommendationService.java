package com.learn.spring.l09_profiles_conditions;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@ConditionalOnFeature("recommendations")
public class RecommendationService {
    public List<String> recommendFor(String user) {
        return List.of("Spring in Action", "Clean Code");
    }
}
