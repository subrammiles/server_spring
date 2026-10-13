package com.learn.spring.l10_events;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** Spring publishes its own events too. ContextRefreshedEvent = "all beans are ready". */
@Component
public class ContainerEventListener {

    @EventListener
    public void onStartup(ContextRefreshedEvent event) {
        System.out.println("[container] refreshed: all singletons created, the app is ready");
    }

    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        System.out.println("[container] closing");
    }
}
