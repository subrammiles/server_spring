package com.learn.spring.l11_aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.learn.spring.l11_aop.service.BankAccountService;
import com.learn.spring.l11_aop.service.ExchangeRateClient;
import com.learn.spring.l11_aop.service.InsufficientFundsException;

@SpringJUnitConfig(AopConfig.class)
class AopTest {

    @Autowired
    BankAccountService bank;

    @Autowired
    ExchangeRateClient rates;

    @Autowired
    AuditTrail audit;

    @BeforeEach
    void clear() {
        audit.clear();
    }

    @Test
    void serviceIsProxied() {
        assertThat(AopUtils.isAopProxy(bank)).isTrue();
    }

    @Test
    void beforeAndAfterReturningWrapDeposit() {
        bank.deposit("carol", 10);
        assertThat(audit.entries()).containsExactly("BEFORE deposit[carol, 10.0]", "RETURNED deposit -> 10.0");
    }

    @Test
    void afterThrowingSeesTheExceptionAndCallerStillGetsIt() {
        assertThatThrownBy(() -> bank.withdraw("bob", 1_000_000)).isInstanceOf(InsufficientFundsException.class);
        assertThat(audit.entries()).anyMatch(e -> e.startsWith("THREW withdraw"));
    }

    @Test
    void selfInvocationIsNotIntercepted() {
        bank.monthlyStatement("alice");
        assertThat(audit.entries()).anyMatch(e -> e.startsWith("TIMED monthlyStatement"));

        audit.clear();
        bank.yearlyReport("alice");   // calls this.monthlyStatement() internally
        assertThat(audit.entries()).noneMatch(e -> e.startsWith("TIMED"));
    }

    @Test
    void retryAspectRetriesUntilSuccess() {
        assertThat(rates.usdToInr()).isEqualTo(83.2);
        assertThat(rates.calls()).isEqualTo(3);
    }
}
