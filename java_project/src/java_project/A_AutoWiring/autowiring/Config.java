package java_project.A_AutoWiring.autowiring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public  FooFormatter fooFormatter() {
        return  new FooFormatter();
    }

}
