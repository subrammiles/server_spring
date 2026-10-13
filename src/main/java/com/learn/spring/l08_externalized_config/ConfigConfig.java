package com.learn.spring.l08_externalized_config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @PropertySource adds a .properties file to the Environment.
 * Precedence (highest first): JVM system properties (-Dapp.name=X), OS environment variables,
 * then @PropertySource files. So you can override any value at launch without editing the file.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:l08/app.properties")
public class ConfigConfig {

    /** The bean MUST be named "messageSource" for the ApplicationContext to use it. */
    @Bean
    public MessageSource messageSource() {
        var source = new ResourceBundleMessageSource();
        source.setBasename("l08/messages");        // messages.properties, messages_fr.properties, ...
        source.setDefaultEncoding("UTF-8");
        source.setFallbackToSystemLocale(false);    // unknown locale -> messages.properties
        return source;
    }
}
