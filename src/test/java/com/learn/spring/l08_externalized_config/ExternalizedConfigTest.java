package com.learn.spring.l08_externalized_config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
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

    @Autowired
    ShippingSettings shipping;

    @Autowired
    ConversionService conversion;

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

    @Test
    void customConverterAndBuiltInFormattersConvertPropertyStrings() {
        assertThat(shipping.getFee()).isEqualTo(new Money("EUR", new BigDecimal("4.99")));
        assertThat(shipping.getSaleEnds()).isEqualTo(LocalDate.of(2026, 12, 31));
        assertThat(shipping.getDeliveryWindow()).isEqualTo(Duration.ofHours(48));
    }

    @Test
    void conversionServiceCanBeUsedDirectly() {
        assertThat(conversion.convert("usd 10.50", Money.class)).isEqualTo(new Money("USD", new BigDecimal("10.50")));
        assertThatThrownBy(() -> conversion.convert("ten euros", Money.class))
                .isInstanceOf(ConversionFailedException.class)
                .hasRootCauseMessage("Expected '<CURRENCY> <amount>', got 'ten euros'");
    }
}
