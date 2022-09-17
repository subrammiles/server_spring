package java_project.A_java_based.e_LifecycleCallbacks;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleCallBackEngine {
    public static void main(){
        System.out.println("<<<<<LifeCycleCallBackEngine>>>>");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigA.class);
        Hello1 h1 = ctx.getBean(Hello1.class);

    }
}
