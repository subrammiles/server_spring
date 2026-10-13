package com.learn.spring.l12_validation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Validation rules live on the data itself, declared once and reused everywhere. */
public record UserRegistration(
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank @Email String email,
        @Min(value = 18, message = "must be an adult") int age,
        @StrongPassword String password,
        // @Valid = also validate the nested object (cascading)
        @Valid @NotNull Address address) {
}
