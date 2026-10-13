package com.learn.spring.l08_externalized_config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** @TestPropertySource overrides properties for this test only (highest precedence). */
@SpringJUnitConfig(ConfigConfig.class)
@TestPropertySource(properties = {"app.region=eu-west-1", "app.max-users=10"})
class ExternalizedConfigTest {

    @Autowired
    AppSettings settings;

    @Autowired
    PricingService pricing;

    @Autowired
    MessageSource messages;

    @Test
    void valuesComeFromFileDefaultsSpelAndTestOverrides() {
        assertThat(settings.getName()).isEqualTo("Spring Tutorial Shop");   // from app.properties
        assertThat(settings.getRegion()).isEqualTo("eu-west-1");            // test override beats the default
        assertThat(settings.getMaxUsers()).isEqualTo(10);                   // test override beats the file
        assertThat(settings.getMaxSessions()).isEqualTo(20);                // SpEL used the overridden value
        assertThat(settings.getFeatures()).containsExactly("search", "checkout", "reviews");
        assertThat(settings.welcomeText()).startsWith("Welcome");
        assertThat(pricing.priceAfterDiscount(100)).isEqualTo(85.0);
    }

    @Test
    void messagesAreLocalised() {
        assertThat(messages.getMessage("greeting", new Object[]{"Ana"}, Locale.FRENCH)).isEqualTo("Bonjour, Ana !");
        assertThat(messages.getMessage("greeting", new Object[]{"Ana"}, Locale.GERMAN)).isEqualTo("Hello, Ana!");
    }
}
