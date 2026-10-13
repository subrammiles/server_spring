package com.learn.spring.l19_web_mvc;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global error handling for ALL controllers, so they never need try/catch.
 *
 * Extending ResponseEntityExceptionHandler turns every built-in Spring MVC error
 * (malformed JSON, wrong HTTP method, missing parameter, validation...) into a
 * ProblemDetail, the RFC 9457 standard error format:
 * {"title":"Not Found","status":404,"detail":"Task 9 not found","instance":"/api/tasks/9"}
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /** Our own exception -> 404. */
    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail notFound(TaskNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /** Customise the built-in validation error: add a field -> message map. */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = e.getBody();
        problem.setDetail("Validation failed");
        Map<String, String> errors = new TreeMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
        problem.setProperty("errors", errors);
        return handleExceptionInternal(e, problem, headers, status, request);
    }
}
