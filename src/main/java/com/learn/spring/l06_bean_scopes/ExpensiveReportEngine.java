package com.learn.spring.l06_bean_scopes;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Singletons are normally created EAGERLY at startup (so config errors show up early).
 * @Lazy delays creation until the bean is first requested.
 */
@Component
@Lazy
public class ExpensiveReportEngine {
    public static volatile boolean created = false;

    public ExpensiveReportEngine() {
        created = true;
        System.out.println("  ExpensiveReportEngine created (only now, on first use)");
    }

    public String run() {
        return "report ready";
    }
}
