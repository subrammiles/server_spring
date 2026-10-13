package com.learn.spring.l20_testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.learn.spring.l04_component_scan.AppConfig;
import com.learn.spring.l04_component_scan.NotificationService;
import com.learn.spring.l04_component_scan.OrderService;
import com.learn.spring.l04_component_scan.ReportService;

/**
 * TECHNIQUE 2: an INTEGRATION test with the real container, but one bean swapped for a mock.
 * Real OrderService + real repository, fake NotificationService (no real emails in tests).
 */
@SpringJUnitConfig(AppConfig.class)
class MockitoBeanIntegrationTest {

    @MockitoBean
    NotificationService notifications;   // replaces the real bean in the context

    @Autowired
    OrderService orders;

    @Autowired
    ReportService reports;

    @Test
    void realBeansCollaborateWhileTheEmailSenderIsMocked() {
        orders.placeOrder("Pen", 2.5);

        verify(notifications).notifyCustomer(startsWith("Order #"));
        assertThat(reports.summary()).contains("total = 2.5");
    }
}
