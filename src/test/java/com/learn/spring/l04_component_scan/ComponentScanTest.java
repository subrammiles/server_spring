package com.learn.spring.l04_component_scan;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppConfig.class)
class ComponentScanTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orders;

    @Autowired
    NotificationService notifications;

    @Test
    void scanningRegistersStereotypesUnderDefaultNames() {
        assertThat(context.containsBean("orderService")).isTrue();
        assertThat(context.containsBean("inMemoryOrderRepository")).isTrue();
        assertThat(context.containsBean("legacyAuditor")).isTrue();
    }

    @Test
    void servicesAreWiredTogether() {
        Order order = orders.placeOrder("Mouse", 20);

        assertThat(order.id()).isPositive();
        assertThat(notifications.sentMessages()).anyMatch(m -> m.contains("Mouse"));
        assertThat(context.getBean(ReportService.class).summary()).contains("orders");
    }
}
