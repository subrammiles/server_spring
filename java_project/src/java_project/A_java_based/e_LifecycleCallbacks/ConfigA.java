package java_project.A_java_based.e_LifecycleCallbacks;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class ConfigA {
    @Bean(initMethod = "init",destroyMethod = "cleanup" )
    public Hello1 hello1(){
        return new Hello1();
    }
}
