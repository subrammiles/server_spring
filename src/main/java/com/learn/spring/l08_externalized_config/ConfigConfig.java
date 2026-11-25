package com.learn.spring.l08_externalized_config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

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

    /**
     * TYPE CONVERSION. The bean MUST be named "conversionService" for the container to use it when
     * injecting @Value and other bean properties. Without it Spring only knows the basics (numbers,
     * booleans, arrays, enums, Resources...).
     *
     * DefaultFormattingConversionService = all built-in Converters (String -> int, List, enum, UUID...)
     * plus Formatters for @DateTimeFormat, @NumberFormat and @DurationFormat. We add our own Converter on top.
     */
    @Bean
    public FormattingConversionService conversionService() {
        var service = new DefaultFormattingConversionService();
        service.addConverter(new MoneyConverter());
        return service;
    }
}
