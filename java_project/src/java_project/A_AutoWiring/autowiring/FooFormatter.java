package java_project.A_AutoWiring.autowiring;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("fooFormatter")
public class FooFormatter {


    public  void print(String msg) {
       System.out.println(msg);
    }
}
