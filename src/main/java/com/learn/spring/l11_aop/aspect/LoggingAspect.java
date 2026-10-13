package com.learn.spring.l11_aop.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learn.spring.l11_aop.AuditTrail;

/** An aspect = pointcuts (WHERE) + advice (WHAT and WHEN). */
@Aspect
@Component
@Order(1)
public class LoggingAspect {
    private final AuditTrail audit;

    public LoggingAspect(AuditTrail audit) {
        this.audit = audit;
    }

    @Before("com.learn.spring.l11_aop.aspect.CommonPointcuts.moneyMovement()")
    public void before(JoinPoint jp) {
        audit.add("BEFORE " + jp.getSignature().getName() + Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "com.learn.spring.l11_aop.aspect.CommonPointcuts.moneyMovement()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        audit.add("RETURNED " + jp.getSignature().getName() + " -> " + result);
    }

    @AfterThrowing(pointcut = "com.learn.spring.l11_aop.aspect.CommonPointcuts.serviceLayer()", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Exception ex) {
        audit.add("THREW " + jp.getSignature().getName() + " -> " + ex.getMessage());
    }
}
