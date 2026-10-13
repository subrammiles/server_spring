package com.learn.spring.l15_jdbc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;

public class JdbcDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(JdbcConfig.class)) {
            EmployeeDao dao = context.getBean(EmployeeDao.class);

            System.out.println("count            = " + dao.count());
            dao.findAll().forEach(e -> System.out.println("  " + e));

            Employee saved = dao.insert(Employee.newEmployee("Divya", "SALES", new BigDecimal("72000"), "divya@corp.com"));
            System.out.println("inserted         = " + saved);
            System.out.println("findById(" + saved.id() + ")      = " + dao.findById(saved.id()));
            System.out.println("findById(999)    = " + dao.findById(999));

            System.out.println("engineers >= 110k = " + dao.findByDepartmentEarningAtLeast("ENGINEERING", new BigDecimal("110000")));
            System.out.println("sales names       = " + dao.namesInDepartment("SALES"));
            System.out.println("raised in HR      = " + dao.raiseSalaries("HR", 10) + " row(s)");
            System.out.println("batch inserted    = " + dao.batchInsert(List.of(
                    Employee.newEmployee("Arjun", "HR", new BigDecimal("60000"), null),
                    Employee.newEmployee("Sana", "HR", new BigDecimal("62000"), null))));
            System.out.println("avg by department = " + dao.averageSalaryByDepartment());

            System.out.println("\n--- exception translation ---");
            try {
                dao.insert(Employee.newEmployee("Clone", "HR", BigDecimal.ONE, "asha@corp.com"));
            } catch (DataAccessException e) {
                // H2's SQLException became Spring's database-independent DuplicateKeyException
                System.out.println("duplicate email -> " + e.getClass().getSimpleName());
            }
            try {
                dao.getById(999);
            } catch (DataAccessException e) {
                System.out.println("missing row     -> " + e.getClass().getSimpleName());
            }
        }
    }
}
