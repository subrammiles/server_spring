package com.learn.spring.l07_bean_lifecycle;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * A BeanPostProcessor sees EVERY bean right before and right after initialization.
 * This is how Spring itself adds features: @Autowired, @PostConstruct, AOP proxies (lesson 11),
 * @Async (lesson 13)... are all implemented by post-processors.
 */
public class TracingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof ConnectionPool) {
            LifecycleLog.add("BeanPostProcessor.postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof ConnectionPool) {
            LifecycleLog.add("BeanPostProcessor.postProcessAfterInitialization (a proxy could be returned here)");
        }
        return bean;
    }
}
