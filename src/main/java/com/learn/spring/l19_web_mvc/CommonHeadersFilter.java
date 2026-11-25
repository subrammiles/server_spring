package com.learn.spring.l19_web_mvc;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A SERVLET FILTER runs in Tomcat, BEFORE the DispatcherServlet, for EVERY request: unknown URLs,
 * static files, errors. An interceptor (RequestIdInterceptor) runs inside Spring MVC, and only when a
 * controller method matched.
 *
 *   request -> Filter(s) -> DispatcherServlet -> Interceptor(s) -> Controller
 *
 * Use a filter for cross-cutting HTTP concerns: security (Spring Security is a chain of filters,
 * see server_spring_boot module 05), CORS, compression, request logging, response headers.
 * OncePerRequestFilter (Spring's base class) guarantees one run per request, even on internal forwards.
 */
public class CommonHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        long start = System.nanoTime();
        // Before: headers must be set before the body is written (the response is "committed" after that)
        response.setHeader("X-Content-Type-Options", "nosniff");
        try {
            chain.doFilter(request, response);   // continue to the next filter, then the DispatcherServlet
        } finally {
            // After: the whole request is done, including error handling
            long millis = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[filter] " + request.getMethod() + " " + request.getRequestURI()
                    + " -> " + response.getStatus() + " in " + millis + " ms");
        }
    }
}
