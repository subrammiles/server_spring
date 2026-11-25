package com.learn.spring.l16_transactions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Events + transactions (lesson 10 meets lesson 16).
 *
 * A plain @EventListener runs IMMEDIATELY, in the middle of the publisher's transaction. If that
 * transaction later rolls back, we have already told the customer about a payment that never happened.
 *
 * @TransactionalEventListener holds the event until the transaction finishes and runs only in the
 * chosen phase: AFTER_COMMIT (default), AFTER_ROLLBACK, AFTER_COMPLETION or BEFORE_COMMIT.
 * It needs @EnableTransactionManagement (TxConfig) and works with @Transactional and TransactionTemplate.
 */
@Component
public class PaymentNotifications {
    private final List<String> sent = new CopyOnWriteArrayList<>();

    /** WRONG for side effects: fires even if the payment is rolled back afterwards. */
    @EventListener
    public void immediately(PaymentSent payment) {
        sent.add("immediate: " + payment);
    }

    /** RIGHT: the SMS goes out only once the money has really moved. */
    @TransactionalEventListener
    public void afterCommit(PaymentSent payment) {
        sent.add("after commit: " + payment);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(PaymentSent payment) {
        sent.add("after rollback: " + payment);
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }

    public void clear() {
        sent.clear();
    }
}
