package com.learn.spring.l06_bean_scopes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ScopesTest {

    @Test
    void singletonVsPrototypeVsTheInjectionPitfall() {
        ExpensiveReportEngine.created = false;
        try (var context = new AnnotationConfigApplicationContext(ScopesConfig.class)) {
            assertThat(context.getBean(VisitCounter.class)).isSameAs(context.getBean(VisitCounter.class));
            assertThat(context.getBean(ShoppingCart.class)).isNotSameAs(context.getBean(ShoppingCart.class));

            BrokenCheckoutService broken = context.getBean(BrokenCheckoutService.class);
            assertThat(broken.cartForNewCustomer()).isSameAs(broken.cartForNewCustomer());

            CheckoutService fixed = context.getBean(CheckoutService.class);
            assertThat(fixed.cartForNewCustomer()).isNotSameAs(fixed.cartForNewCustomer());

            assertThat(ExpensiveReportEngine.created).isFalse();
            context.getBean(ExpensiveReportEngine.class);
            assertThat(ExpensiveReportEngine.created).isTrue();
        }
    }
}
