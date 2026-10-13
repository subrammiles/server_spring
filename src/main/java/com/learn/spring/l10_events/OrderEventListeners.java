package com.learn.spring.l10_events;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListeners {
    private final EventLog log;

    public OrderEventListeners(EventLog log) {
        this.log = log;
    }

    @EventListener
    @Order(1)   // lower value runs first
    public void reserveStock(OrderPlacedEvent event) {
        log.add("inventory: stock reserved for order " + event.orderId());
    }

    @EventListener
    @Order(2)
    public void sendConfirmationEmail(OrderPlacedEvent event) {
        log.add("email: confirmation sent to " + event.customer());
    }

    /** condition = SpEL evaluated against the event; the listener runs only when it is true. */
    @EventListener(condition = "#event.amount > 1000")
    @Order(3)
    public void flagForFraudReview(OrderPlacedEvent event) {
        log.add("fraud: order " + event.orderId() + " flagged (amount " + event.amount() + ")");
    }

    /** A non-void listener: the returned object is published as a NEW event (event chaining). */
    @EventListener
    @Order(4)
    public LoyaltyPointsEarnedEvent awardPoints(OrderPlacedEvent event) {
        int points = (int) (event.amount() / 10);
        log.add("loyalty: " + points + " points for " + event.customer());
        return new LoyaltyPointsEarnedEvent(event.customer(), points);
    }

    @EventListener
    public void congratulate(LoyaltyPointsEarnedEvent event) {
        log.add("loyalty-notifier: told " + event.customer() + " about " + event.points() + " new points");
    }
}
