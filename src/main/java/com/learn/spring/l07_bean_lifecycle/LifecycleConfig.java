package com.learn.spring.l07_bean_lifecycle;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifecycleConfig {

    /**
     * A BeanFactoryPostProcessor runs BEFORE any normal bean is created and can change bean
     * DEFINITIONS (this is how ${...} placeholders get resolved). Declare it static so it does not
     * force this config class to be created early.
     */
    @Bean
    public static BeanFactoryPostProcessor definitionCounter() {
        return factory -> LifecycleLog.add("BeanFactoryPostProcessor: "
                + factory.getBeanDefinitionCount() + " bean definitions, no beans created yet");
    }

    @Bean
    public static TracingBeanPostProcessor tracingBeanPostProcessor() {
        return new TracingBeanPostProcessor();
    }

    @Bean
    public PoolSettings poolSettings() {
        return new PoolSettings(5);
    }

    @Bean(initMethod = "openConnections", destroyMethod = "closeConnections")
    public ConnectionPool connectionPool() {
        return new ConnectionPool();
    }
}
