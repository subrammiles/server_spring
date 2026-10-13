package com.learn.spring.l04_component_scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * No @Bean methods at all: @ComponentScan finds every @Component / @Service / @Repository /
 * @Controller / @Configuration in this package (and sub-packages) and registers it.
 */
@Configuration
@ComponentScan
public class AppConfig {
}
