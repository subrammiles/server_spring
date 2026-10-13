package com.learn.spring.l12_validation;

import java.util.Comparator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class ValidationDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ValidationConfig.class)) {
            var good = new UserRegistration("subbu", "subbu@example.com", 30, "Secret123", new Address("Hyderabad", "500001"));
            var bad = new UserRegistration("x", "not-an-email", 12, "weak", new Address("", "12"));

            System.out.println("--- programmatic validation ---");
            Validator validator = context.getBean(Validator.class);
            System.out.println("good -> " + validator.validate(good).size() + " violations");
            validator.validate(bad).stream()
                    .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                    .forEach(v -> System.out.println("bad  -> " + v.getPropertyPath() + ": " + v.getMessage()));

            System.out.println("\n--- method validation (@Validated service) ---");
            UserService users = context.getBean(UserService.class);
            System.out.println(users.register(good));
            try {
                users.findUser("  ");
            } catch (ConstraintViolationException e) {
                for (ConstraintViolation<?> v : e.getConstraintViolations()) {
                    System.out.println("rejected before the method ran: " + v.getPropertyPath() + " " + v.getMessage());
                }
            }
        }
    }
}
