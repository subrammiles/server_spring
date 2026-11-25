package com.learn.spring.l19_web_mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @EnableWebMvc sets up Spring MVC: handler mappings, JSON conversion (Jackson is on the
 * classpath), validation (Hibernate Validator is on the classpath), exception resolvers.
 * WebMvcConfigurer callbacks customise it.
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig implements WebMvcConfigurer {

    /**
     * Parses multipart/form-data requests (file uploads) using the servlet container's built-in support.
     * The DispatcherServlet looks for a bean with exactly this name.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestIdInterceptor()).addPathPatterns("/**");
    }

    /** CORS: allow a browser app served from another origin (e.g. a React dev server) to call the API. */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
