package com.learn.spring.l19_web_mvc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * What clients send. A separate type from Task, so clients cannot set the id.
 * done is a Boolean (not boolean) so it may be left out of the JSON: null means "not done".
 */
public record TaskRequest(@NotBlank @Size(max = 100) String title, Boolean done) {

    public boolean isDone() {
        return Boolean.TRUE.equals(done);
    }
}
