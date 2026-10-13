package com.learn.spring.l11_aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.learn.spring.l11_aop.AuditTrail;
import com.learn.spring.l11_aop.Retry;

@Aspect
@Component
public class RetryAspect {
    private final AuditTrail audit;

    public RetryAspect(AuditTrail audit) {
        this.audit = audit;
    }

    /** Binding the annotation as a parameter gives access to its attributes (attempts). */
    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint pjp, Retry retry) throws Throwable {
        for (int attempt = 1; ; attempt++) {
            try {
                return pjp.proceed();
            } catch (RuntimeException e) {
                if (attempt >= retry.attempts()) {
                    throw e;
                }
                audit.add("RETRY " + pjp.getSignature().getName() + " attempt " + attempt + " failed: " + e.getMessage());
            }
        }
    }
}
