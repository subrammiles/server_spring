package java_project.A_AutoWiring.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooService {
    @Autowired
    private FooFormatter fooFormatter;

    public FooService(FooFormatter fooFormatter) {
        this.fooFormatter = fooFormatter;
        fooFormatter.print("I am message from FooService:Autowiring test");
    }
    FooService(){

    }
}

