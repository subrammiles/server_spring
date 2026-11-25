package com.learn.spring.l04_component_scan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learn.spring.l04_component_scan.circular.Cycles.Billing;
import com.learn.spring.l04_component_scan.circular.Cycles.BillingDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.LazyBillingDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.LazyOrderDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.OrderDesk;
import com.learn.spring.l04_component_scan.circular.Cycles.Orders;
import com.learn.spring.l04_component_scan.circular.Cycles.TaxRules;

class CircularDependencyTest {

    @Test
    void constructorCycleFailsAtStartup() {
        assertThatThrownBy(() -> new AnnotationConfigApplicationContext(OrderDesk.class, BillingDesk.class))
                .isInstanceOf(UnsatisfiedDependencyException.class)
                .rootCause().isInstanceOf(BeanCurrentlyInCreationException.class);
    }

    @Test
    void lazyInjectsAProxyAndBreaksTheCycle() {
        try (var context = new AnnotationConfigApplicationContext(LazyOrderDesk.class, LazyBillingDesk.class)) {
            LazyOrderDesk orders = context.getBean(LazyOrderDesk.class);

            assertThat(orders.place("Mouse")).isEqualTo("order for Mouse, invoice for Mouse");
            assertThat(orders.injectedBillingClass()).isNotEqualTo(LazyBillingDesk.class);
            assertThat(orders.injectedBillingClass().getName()).contains("SpringCGLIB");
        }
    }

    @Test
    void extractingTheSharedLogicRemovesTheCycle() {
        try (var context = new AnnotationConfigApplicationContext(TaxRules.class, Orders.class, Billing.class)) {
            assertThat(context.getBean(Orders.class).place("Mouse", 10)).isEqualTo("order for Mouse at 11.8");
            assertThat(context.getBean(Billing.class).invoice("Mouse", 10)).isEqualTo("invoice for Mouse: 11.8");
        }
    }
}
