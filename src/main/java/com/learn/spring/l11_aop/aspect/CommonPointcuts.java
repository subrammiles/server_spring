package com.learn.spring.l11_aop.aspect;

import org.aspectj.lang.annotation.Pointcut;

/** Named, reusable pointcut expressions. A pointcut answers "WHERE should advice apply?". */
public class CommonPointcuts {

    /** Every method of every class in the service package. */
    @Pointcut("within(com.learn.spring.l11_aop.service..*)")
    public void serviceLayer() {
    }

    /** Only BankAccountService methods whose name starts with "withdraw" or "deposit". */
    @Pointcut("execution(* com.learn.spring.l11_aop.service.BankAccountService.withdraw(..))"
            + " || execution(* com.learn.spring.l11_aop.service.BankAccountService.deposit(..))")
    public void moneyMovement() {
    }
}
