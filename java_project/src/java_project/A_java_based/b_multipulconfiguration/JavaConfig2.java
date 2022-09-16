package java_project.A_java_based.b_multipulconfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig2 {

    @Bean
    public HelloWorld2 helloWorld2(){
        return new HelloWorld2();
    }
    /*
    The @Bean annotation tells Spring that a method annotated with @Bean will return an object that should be
    registered as a bean in the Spring application context.
    * */

}
/*
The above code will be equivalent to the following XML configuration −
<beans>
   <bean id = "helloWorld" class = "java_project.A_java_based.a_simple.HelloWorld" />
</beans>
* */