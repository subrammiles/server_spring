package java_project.A_java_based.a_simple;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Annotating a class with the @Configuration indicates that the class can be used by the Spring IoC container as a source of bean definitions.
The @Bean annotation tells Spring that a method annotated with @Bean will return an object that should be registered as a bean in the
Spring application context.

* */
@Configuration
public class SimpleJavaConfig {

    @Bean
    public HelloWorld helloWorld(){
        return new HelloWorld();
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
