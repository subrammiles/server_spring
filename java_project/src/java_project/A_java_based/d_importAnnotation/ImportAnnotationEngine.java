package java_project.A_java_based.d_importAnnotation;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportAnnotationEngine {
    public static void main(){
        System.out.println("<<<<<ImportAnnotationEngine>>>>");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigA.class);
        Hello1 h1 = ctx.getBean(Hello1.class);
        Hello2 h2 = ctx.getBean(Hello2.class);
    }
}
