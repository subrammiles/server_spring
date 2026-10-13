package com.learn.spring.l12_validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * METHOD validation: @Validated on the class makes Spring proxy it (AOP again!) and check
 * parameter constraints before the method body runs. Bad input never reaches the business logic.
 */
@Service
@Validated
public class UserService {

    public String register(@Valid UserRegistration registration) {
        return "registered " + registration.username();
    }

    public String findUser(@NotBlank String username) {
        return "found " + username;
    }
}
