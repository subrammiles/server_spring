package com.learn.spring.l11_aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/** @EnableAspectJAutoProxy registers the BeanPostProcessor that wraps matching beans in proxies. */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AopConfig {
}
