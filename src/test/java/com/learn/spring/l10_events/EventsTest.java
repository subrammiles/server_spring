package com.learn.spring.l10_events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** @RecordApplicationEvents captures every event published during each test. */
@SpringJUnitConfig(EventsConfig.class)
@RecordApplicationEvents
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // fresh EventLog per test
class EventsTest {

    @Autowired
    OrderService orders;

    @Autowired
    EventLog log;

    @Autowired
    ApplicationEvents events;

    @Test
    void smallOrderRunsListenersInOrderAndChainsLoyaltyEvent() {
        orders.placeOrder("alice", 250);

        assertThat(events.stream(OrderPlacedEvent.class)).hasSize(1);
        assertThat(events.stream(LoyaltyPointsEarnedEvent.class))
                .containsExactly(new LoyaltyPointsEarnedEvent("alice", 25));
        assertThat(log.entries()).extracting(e -> e.split(":")[0])
                .containsExactly("inventory", "email", "loyalty", "loyalty-notifier");
    }

    @Test
    void bigOrderAlsoTriggersConditionalFraudListener() {
        orders.placeOrder("bob", 5000);

        assertThat(log.entries()).anyMatch(e -> e.startsWith("fraud"));
    }
}
