package com.learn.spring.l12_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.length() >= 8
                && value.chars().anyMatch(Character::isUpperCase)
                && value.chars().anyMatch(Character::isDigit);
    }
}
