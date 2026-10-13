package com.learn.spring.l09_profiles_conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class DefaultProfileTest {

    @Test
    void noProfileMeansDefaultProfileAndNoFeatureMeansNoBean() {
        try (var context = ProfilesConfig.start(null, Map.of())) {
            assertThat(context.getBean(StorageService.class)).isInstanceOf(LocalDiskStorage.class);
            assertThat(context.getBeansOfType(RecommendationService.class)).isEmpty();
        }
    }
}
