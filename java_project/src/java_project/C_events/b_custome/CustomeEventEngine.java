package java_project.C_events.b_custome;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomeEventEngine {
    public static void main() {
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("customEvents.xml");
        CustomEventPublisher cvp =
                (CustomEventPublisher) context.getBean("customEventPublisher");
        cvp.publish();
        cvp.publish();
    }
}
