package com.learn.spring.l07_bean_lifecycle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class LifecycleTest {

    @Test
    void callbacksRunInTheDocumentedOrder() {
        LifecycleLog.clear();
        new AnnotationConfigApplicationContext(LifecycleConfig.class).close();

        assertThat(LifecycleLog.steps())
                .map(step -> step.split("[:(]")[0].trim())
                .containsExactly(
                        "BeanFactoryPostProcessor",
                        "constructor",
                        "dependency injected",
                        "BeanNameAware.setBeanName",
                        "ApplicationContextAware.setApplicationContext",
                        "BeanPostProcessor.postProcessBeforeInitialization",
                        "@PostConstruct",
                        "InitializingBean.afterPropertiesSet",
                        "@Bean",
                        "BeanPostProcessor.postProcessAfterInitialization",
                        "@PreDestroy",
                        "DisposableBean.destroy",
                        "@Bean");
    }
}
