package com.learn.spring.l16_transactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** No @Transactional here on purpose: we want to observe REAL commits and rollbacks. */
@SpringJUnitConfig(TxConfig.class)
class TransferServiceTest {

    @Autowired
    TransferService transfers;

    @Autowired
    BulkPaymentService bulk;

    @Autowired
    AccountRepository accounts;

    @Autowired
    AuditService audit;

    @BeforeEach
    void reset() {
        accounts.reset();
    }

    private BigDecimal alice() {
        return accounts.balanceOf("ACC-1");
    }

    @Test
    void withoutTransactionAPartialFailureLosesMoney() {
        assertThatThrownBy(() -> transfers.transferWithoutTransaction("ACC-1", "NOPE", new BigDecimal("100")))
                .isInstanceOf(AccountNotFoundException.class);
        assertThat(alice()).isEqualByComparingTo("900");
    }

    @Test
    void runtimeExceptionRollsBackButRequiresNewAuditSurvives() {
        assertThatThrownBy(() -> transfers.transfer("ACC-1", "NOPE", new BigDecimal("100")))
                .isInstanceOf(AccountNotFoundException.class);
        assertThat(alice()).isEqualByComparingTo("1000");
        assertThat(audit.entries()).containsExactly("transfer 100 ACC-1 -> NOPE");
    }

    @Test
    void checkedExceptionCommitsByDefault() {
        assertThatThrownBy(() -> transfers.transferWithFraudCheck("ACC-1", "ACC-2", new BigDecimal("450")))
                .isInstanceOf(FraudSuspectedException.class);
        assertThat(alice()).isEqualByComparingTo("550");
    }

    @Test
    void rollbackForMakesCheckedExceptionRollBack() {
        assertThatThrownBy(() -> transfers.safeTransferWithFraudCheck("ACC-1", "ACC-2", new BigDecimal("450")))
                .isInstanceOf(FraudSuspectedException.class);
        assertThat(alice()).isEqualByComparingTo("1000");
    }

    @Test
    void insufficientFundsLeavesBalancesUntouched() {
        assertThatThrownBy(() -> transfers.transfer("ACC-2", "ACC-1", new BigDecimal("501")))
                .isInstanceOf(InsufficientFundsException.class);
        assertThat(transfers.totalMoneyInBank()).isEqualByComparingTo("1500");
    }

    @Test
    void transactionTemplateCommitsOrRollsBackProgrammatically() {
        assertThat(bulk.payAll("ACC-1", Map.of("ACC-2", new BigDecimal("300")), new BigDecimal("200"))).isFalse();
        assertThat(alice()).isEqualByComparingTo("1000");

        assertThat(bulk.payAll("ACC-1", Map.of("ACC-2", new BigDecimal("150")), new BigDecimal("200"))).isTrue();
        assertThat(alice()).isEqualByComparingTo("850");
    }
}
