package com.learn.spring.l05_autowiring_choices;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/** Shows every way to choose between (or collect) several beans of one type. */
@Service
public class CheckoutService {
    private final PaymentGateway defaultGateway;   // @Primary wins -> Stripe
    private final PaymentGateway paypal;           // @Qualifier by bean name
    private final PaymentGateway experimental;     // custom qualifier annotation
    private final List<PaymentGateway> all;        // every bean of the type, sorted by @Order
    private final Map<String, PaymentGateway> byName; // bean name -> bean
    private final ObjectProvider<FraudChecker> fraudChecker; // may be missing

    public CheckoutService(PaymentGateway defaultGateway,
                           @Qualifier("paypal") PaymentGateway paypal,
                           @Experimental PaymentGateway experimental,
                           List<PaymentGateway> all,
                           Map<String, PaymentGateway> byName,
                           ObjectProvider<FraudChecker> fraudChecker) {
        this.defaultGateway = defaultGateway;
        this.paypal = paypal;
        this.experimental = experimental;
        this.all = all;
        this.byName = byName;
        this.fraudChecker = fraudChecker;
    }

    public String checkout(double amount) {
        // getIfAvailable returns null when no FraudChecker bean exists, instead of failing startup
        FraudChecker checker = fraudChecker.getIfAvailable();
        if (checker != null && checker.isSuspicious(amount)) {
            return "blocked";
        }
        return defaultGateway.pay(amount);
    }

    public String payWithPaypal(double amount) {
        return paypal.pay(amount);
    }

    public String payWithExperimental(double amount) {
        return experimental.pay(amount);
    }

    public List<String> gatewayClassesInOrder() {
        return all.stream().map(g -> g.getClass().getSimpleName()).toList();
    }

    public String payWith(String beanName, double amount) {
        PaymentGateway gateway = byName.get(beanName);
        if (gateway == null) {
            throw new IllegalArgumentException("Unknown gateway " + beanName + ", known: " + byName.keySet());
        }
        return gateway.pay(amount);
    }
}
