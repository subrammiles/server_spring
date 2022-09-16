package java_project.A_java_based.d_importAnnotation;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConfigB.class)
public class ConfigA {
    @Bean
    public Hello1 hello1(){
        return new Hello1();
    }
}
