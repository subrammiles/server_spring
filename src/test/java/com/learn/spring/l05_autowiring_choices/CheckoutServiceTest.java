package com.learn.spring.l05_autowiring_choices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AutowiringConfig.class)
class CheckoutServiceTest {

    @Autowired
    CheckoutService checkout;

    @Test
    void primaryQualifierAndCustomQualifierPickDifferentBeans() {
        assertThat(checkout.checkout(10)).startsWith("Stripe");
        assertThat(checkout.payWithPaypal(10)).startsWith("PayPal");
        assertThat(checkout.payWithExperimental(10)).startsWith("Crypto");
    }

    @Test
    void listIsSortedByOrderAndMapIsKeyedByBeanName() {
        assertThat(checkout.gatewayClassesInOrder()).containsExactly("StripeGateway", "PaypalGateway", "CryptoGateway");
        assertThat(checkout.payWith("cryptoGateway", 5)).startsWith("Crypto");
        assertThatThrownBy(() -> checkout.payWith("bitcoin", 5)).isInstanceOf(IllegalArgumentException.class);
    }
}
