package java_project.A_AutoWiring.autowiring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoWiredBasicEngine {

    public static void main(){
        /*We defined the beans in the SimpleJavaConfig class*/
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        FooFormatter fooFormatter= ctx.getBean(FooFormatter.class);;
        fooFormatter.print("I am message from FooFormatter:Autowiring test");
        FooService fooService=new FooService(fooFormatter);


    }
}
