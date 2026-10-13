package com.learn.spring.l11_aop;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learn.spring.l11_aop.service.BankAccountService;
import com.learn.spring.l11_aop.service.ExchangeRateClient;

public class AopDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AopConfig.class)) {
            BankAccountService bank = context.getBean(BankAccountService.class);
            System.out.println("Is a proxy? " + AopUtils.isAopProxy(bank) + " -> " + bank.getClass().getSimpleName());

            System.out.println("\n--- @Before / @AfterReturning ---");
            bank.deposit("alice", 25);

            System.out.println("\n--- @AfterThrowing ---");
            try {
                bank.withdraw("bob", 999);
            } catch (RuntimeException e) {
                System.out.println("caller still receives the exception: " + e.getClass().getSimpleName());
            }

            System.out.println("\n--- @Around timing ---");
            System.out.println(bank.monthlyStatement("alice"));

            System.out.println("\n--- self-invocation: NO 'TIMED' line for the inner call ---");
            System.out.println(bank.yearlyReport("alice"));

            System.out.println("\n--- @Around retry ---");
            System.out.println("rate = " + context.getBean(ExchangeRateClient.class).usdToInr());
        }
    }
}
