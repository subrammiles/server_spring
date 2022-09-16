package java_project.A_java_based.c_InjectingBeanDependencies;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectingBeanEngine {
    public static void main(){
        System.out.println("<<<<<InjectingBeanEngine>>>>");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        Foo foo = ctx.getBean(Foo.class);
        foo.addTwoNumbers(2,3);
    }
}
