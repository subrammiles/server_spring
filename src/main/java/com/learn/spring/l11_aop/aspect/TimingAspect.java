package com.learn.spring.l11_aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learn.spring.l11_aop.AuditTrail;

@Aspect
@Component
@Order(2)
public class TimingAspect {
    private final AuditTrail audit;

    public TimingAspect(AuditTrail audit) {
        this.audit = audit;
    }

    /** @Around wraps the call: code before, pjp.proceed() runs the real method, code after. */
    @Around("@annotation(com.learn.spring.l11_aop.LogExecutionTime)")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            audit.add("TIMED " + pjp.getSignature().getName() + " took " + ms + " ms");
        }
    }
}
