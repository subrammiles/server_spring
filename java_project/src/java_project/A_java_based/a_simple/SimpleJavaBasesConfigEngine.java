package java_project.A_java_based.a_simple;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
Annotating a class with the @Configuration indicates that the class can be used by the
Spring IoC container as a source of bean definitions. The @Bean annotation tells Spring that
a method annotated with @Bean will return an object that should be registered as a bean in the Spring application context.
* */
public class SimpleJavaBasesConfigEngine {

    public static void main(){
        /*We defined the beans in the SimpleJavaConfig class*/
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleJavaConfig.class);
        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        helloWorld.printMessage();
    }
}
