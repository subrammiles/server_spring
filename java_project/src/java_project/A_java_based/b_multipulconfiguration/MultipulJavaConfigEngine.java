package java_project.A_java_based.b_multipulconfiguration;



import org.springframework.context.annotation.AnnotationConfigApplicationContext;




public class MultipulJavaConfigEngine {
    public static void main(){
        System.out.println("<<<< Multiple Java Configs>>>>>>> ");
        /*We defined the beans in the JavaConfig1 class and also JavaConfig2*/
        AnnotationConfigApplicationContext  ctx = new AnnotationConfigApplicationContext();
        ctx.register(JavaConfig1.class,JavaConfig2.class);
        ctx.refresh();

        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        helloWorld.printMessage();
        HelloWorld2 helloWorld2 = ctx.getBean(HelloWorld2.class);
        helloWorld2.printMessage();
    }
}

