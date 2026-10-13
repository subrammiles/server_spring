package com.learn.spring.l07_bean_lifecycle;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Uses EVERY lifecycle hook so you can see the order Spring calls them in.
 * In real code pick ONE init and ONE destroy style: @PostConstruct / @PreDestroy is the usual choice.
 */
public class ConnectionPool implements BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {
    private PoolSettings settings;
    private boolean open;

    public ConnectionPool() {
        LifecycleLog.add("constructor");
    }

    @Autowired
    public void setSettings(PoolSettings settings) {
        this.settings = settings;
        LifecycleLog.add("dependency injected (setter)");
    }

    @Override
    public void setBeanName(String name) {
        LifecycleLog.add("BeanNameAware.setBeanName(" + name + ")");
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        LifecycleLog.add("ApplicationContextAware.setApplicationContext");
    }

    @PostConstruct
    public void postConstruct() {
        LifecycleLog.add("@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        LifecycleLog.add("InitializingBean.afterPropertiesSet");
    }

    /** Referenced by @Bean(initMethod = "openConnections"). */
    public void openConnections() {
        open = true;
        LifecycleLog.add("@Bean(initMethod) openConnections: " + settings.size() + " connections");
    }

    public boolean isOpen() {
        return open;
    }

    @PreDestroy
    public void preDestroy() {
        LifecycleLog.add("@PreDestroy");
    }

    @Override
    public void destroy() {
        LifecycleLog.add("DisposableBean.destroy");
    }

    /** Referenced by @Bean(destroyMethod = "closeConnections"). */
    public void closeConnections() {
        open = false;
        LifecycleLog.add("@Bean(destroyMethod) closeConnections");
    }
}
