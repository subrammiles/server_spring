package java_project.C_events.a_applicationcontextevents;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationEventTestEngine {

    public static void main() {
        System.out.println("<<<<<<ApplicationEventTestEngine>>>>>.");
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("applicationcontextevents.xml");

        // Let us raise a start event.
        context.start();

        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();

        // Let us raise a stop event.
        context.stop();
    }
}
