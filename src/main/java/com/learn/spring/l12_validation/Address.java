package com.learn.spring.l12_validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Address(
        @NotBlank String city,
        @Pattern(regexp = "\\d{6}", message = "must be a 6-digit PIN code") String pinCode) {
}
