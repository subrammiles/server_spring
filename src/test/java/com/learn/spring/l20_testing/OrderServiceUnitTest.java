package com.learn.spring.l20_testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.spring.l04_component_scan.NotificationService;
import com.learn.spring.l04_component_scan.Order;
import com.learn.spring.l04_component_scan.OrderRepository;
import com.learn.spring.l04_component_scan.OrderService;

/**
 * TECHNIQUE 1: a plain UNIT test. No Spring container at all, so it runs in milliseconds.
 * Possible only because OrderService uses constructor injection (lesson 04).
 * Mockito creates fakes for the dependencies; we script them (when) and check calls (verify).
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTest {

    @Mock
    OrderRepository repository;

    @Mock
    NotificationService notifications;

    @InjectMocks
    OrderService service;   // = new OrderService(repository, notifications)

    @Test
    void placingAnOrderSavesItAndNotifiesTheCustomer() {
        when(repository.save("Book", 15.0)).thenReturn(new Order(7, "Book", 15.0));

        Order order = service.placeOrder("Book", 15.0);

        assertThat(order.id()).isEqualTo(7);
        verify(notifications).notifyCustomer(contains("Order #7"));
    }

    @Test
    void invalidAmountIsRejectedBeforeTouchingDependencies() {
        assertThatThrownBy(() -> service.placeOrder("Book", 0)).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(repository, notifications);
    }
}
