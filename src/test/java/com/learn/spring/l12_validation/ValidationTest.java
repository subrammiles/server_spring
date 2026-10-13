package com.learn.spring.l12_validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@SpringJUnitConfig(ValidationConfig.class)
class ValidationTest {

    @Autowired
    Validator validator;

    @Autowired
    UserService users;

    private static UserRegistration valid() {
        return new UserRegistration("subbu", "subbu@example.com", 30, "Secret123", new Address("Hyderabad", "500001"));
    }

    @Test
    void validObjectHasNoViolations() {
        assertThat(validator.validate(valid())).isEmpty();
    }

    @Test
    void everyBrokenRuleIsReportedIncludingNestedAndCustomOnes() {
        var bad = new UserRegistration("x", "nope", 12, "weak", new Address("", "12"));

        assertThat(validator.validate(bad))
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder("username", "email", "age", "password", "address.city", "address.pinCode");
    }

    @Test
    void customConstraintMessage() {
        var weak = new UserRegistration("subbu", "subbu@example.com", 30, "password", new Address("Hyderabad", "500001"));

        assertThat(validator.validate(weak)).extracting(ConstraintViolation::getMessage)
                .containsExactly("must be 8+ characters with an upper-case letter and a digit");
    }

    @Test
    void methodValidationRejectsBadArguments() {
        assertThat(users.register(valid())).isEqualTo("registered subbu");
        assertThatThrownBy(() -> users.findUser(" ")).isInstanceOf(ConstraintViolationException.class);
    }
}
