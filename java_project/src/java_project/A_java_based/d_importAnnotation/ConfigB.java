package java_project.A_java_based.d_importAnnotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigB {

    @Bean
    public Hello2 hello2(){
        return new Hello2();
    }

}
