package com.learn.spring.l16_transactions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionsDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(TxConfig.class)) {
            TransferService transfers = context.getBean(TransferService.class);
            AccountRepository accounts = context.getBean(AccountRepository.class);
            AuditService audit = context.getBean(AuditService.class);
            BigDecimal hundred = new BigDecimal("100");

            scenario("1. No transaction, credit to a missing account fails", accounts,
                    () -> transfers.transferWithoutTransaction("ACC-1", "NOPE", hundred));
            System.out.println("   -> alice lost 100 and nobody received it. Money vanished!");

            accounts.reset();
            scenario("2. @Transactional, same failure", accounts,
                    () -> transfers.transfer("ACC-1", "NOPE", hundred));
            System.out.println("   -> rolled back, alice still has 1000. Audit entries kept by REQUIRES_NEW: " + audit.entries());

            accounts.reset();
            scenario("3. @Transactional + CHECKED exception", accounts,
                    () -> transfers.transferWithFraudCheck("ACC-1", "ACC-2", new BigDecimal("450")));
            System.out.println("   -> debit COMMITTED even though the method failed (checked exceptions don't roll back)");

            accounts.reset();
            scenario("4. rollbackFor = FraudSuspectedException.class", accounts,
                    () -> transfers.safeTransferWithFraudCheck("ACC-1", "ACC-2", new BigDecimal("450")));

            accounts.reset();
            scenario("5. Successful transfer", accounts, () -> transfers.transfer("ACC-1", "ACC-2", hundred));

            accounts.reset();
            BulkPaymentService bulk = context.getBean(BulkPaymentService.class);
            scenario("6. TransactionTemplate, limit exceeded -> setRollbackOnly()", accounts,
                    () -> System.out.println("   committed? " + bulk.payAll("ACC-1",
                            Map.of("ACC-2", new BigDecimal("300")), new BigDecimal("200"))));

            System.out.println("\nTotal money in bank (readOnly tx): " + transfers.totalMoneyInBank());
        }
    }

    interface Action {
        void run() throws Exception;
    }

    private static void scenario(String title, AccountRepository accounts, Action action) {
        System.out.println("\n" + title);
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("   failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println("   alice=" + accounts.balanceOf("ACC-1") + "  bob=" + accounts.balanceOf("ACC-2"));
    }
}
