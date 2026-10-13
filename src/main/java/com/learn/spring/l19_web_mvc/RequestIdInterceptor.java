package com.learn.spring.l19_web_mvc;

import java.util.UUID;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * An interceptor runs around controller calls (like AOP for the web layer):
 * preHandle -> controller -> postHandle -> afterCompletion.
 * Typical uses: request ids, logging, timing, simple auth checks.
 */
public class RequestIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        response.setHeader("X-Request-Id", requestId);
        System.out.println("[" + requestId + "] " + request.getMethod() + " " + request.getRequestURI());
        return true;   // false would stop the request here
    }
}
