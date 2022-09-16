package java_project.A_basics.a_helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldEngine1 {

    public static void main(){
        helloWorld();
    }

    public static void helloWorld(){
        ApplicationContext context = new ClassPathXmlApplicationContext("HelloWorldBeans.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();
    }

}
