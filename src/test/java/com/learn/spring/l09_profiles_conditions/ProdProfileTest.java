package com.learn.spring.l09_profiles_conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ProfilesConfig.class)
@ActiveProfiles("prod")
@TestPropertySource(properties = "features.recommendations=true")
class ProdProfileTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    StorageService storage;

    @Test
    void prodUsesCloudStorageAndHasNoDebugTools() {
        assertThat(storage).isInstanceOf(CloudStorage.class);
        assertThat(context.getBeansOfType(DebugTools.class)).isEmpty();
    }

    @Test
    void featureFlagTurnsTheConditionalBeanOn() {
        assertThat(context.getBean(RecommendationService.class).recommendFor("x")).isNotEmpty();
    }
}
