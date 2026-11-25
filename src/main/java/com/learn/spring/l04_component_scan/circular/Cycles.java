package com.learn.spring.l04_component_scan.circular;

import org.springframework.context.annotation.Lazy;

/**
 * CIRCULAR DEPENDENCIES: A needs B and B needs A.
 *
 * These classes have NO stereotype annotation on purpose, so lesson 04's @ComponentScan ignores them
 * (otherwise the broken pair would stop the whole lesson from starting). CircularDependencyDemo and
 * the test register them explicitly instead.
 */
public final class Cycles {

    private Cycles() {
    }

    // ---------------------------------------------------------------------------------------
    // 1. BROKEN: constructor injection in both directions.
    //    To build OrderDesk Spring needs a BillingDesk, which needs an OrderDesk, which is
    //    "currently in creation"... Spring gives up with BeanCurrentlyInCreationException.
    // ---------------------------------------------------------------------------------------

    public static class OrderDesk {
        private final BillingDesk billing;

        public OrderDesk(BillingDesk billing) {
            this.billing = billing;
        }
    }

    public static class BillingDesk {
        private final OrderDesk orders;

        public BillingDesk(OrderDesk orders) {
            this.orders = orders;
        }
    }

    // ---------------------------------------------------------------------------------------
    // 2. WORKAROUND: @Lazy on one constructor parameter.
    //    Spring injects a PROXY instead of the real bean. The real LazyBillingDesk is created
    //    on the first method call, after both beans exist. It works, but it hides the design problem.
    // ---------------------------------------------------------------------------------------

    public static class LazyOrderDesk {
        private final LazyBillingDesk billing;

        public LazyOrderDesk(@Lazy LazyBillingDesk billing) {
            this.billing = billing;
        }

        public String place(String item) {
            return "order for " + item + ", " + billing.invoice(item);
        }

        public Class<?> injectedBillingClass() {
            return billing.getClass();   // a CGLIB subclass, not LazyBillingDesk itself
        }
    }

    public static class LazyBillingDesk {
        private final LazyOrderDesk orders;

        public LazyBillingDesk(LazyOrderDesk orders) {
            this.orders = orders;
        }

        public String invoice(String item) {
            return "invoice for " + item;
        }
    }

    // ---------------------------------------------------------------------------------------
    // 3. THE REAL FIX: a cycle usually means a responsibility is in the wrong place.
    //    Both desks needed the other only for the tax calculation, so move that into a
    //    third bean that both depend on. The graph is a tree again: no proxies, no tricks.
    //    (Another option: one side publishes an event instead of calling the other, lesson 10.)
    // ---------------------------------------------------------------------------------------

    public static class TaxRules {
        public double withTax(double net) {
            return Math.round(net * 1.18 * 100) / 100.0;
        }
    }

    public static class Orders {
        private final TaxRules tax;

        public Orders(TaxRules tax) {
            this.tax = tax;
        }

        public String place(String item, double net) {
            return "order for " + item + " at " + tax.withTax(net);
        }
    }

    public static class Billing {
        private final TaxRules tax;

        public Billing(TaxRules tax) {
            this.tax = tax;
        }

        public String invoice(String item, double net) {
            return "invoice for " + item + ": " + tax.withTax(net);
        }
    }
}
