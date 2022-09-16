package java_project.A_annotation_based.c_componentAnnotations;


import org.springframework.stereotype.Component;


/*You can load a bean by adding  @Component("<name>") to your class */
/*PASSING NAME TO THE Component is optionla.It will take the Class name in camercase if you don't pass */
@Component("myTestAnnotationComponent")
public class MyTestAnnotationComponent {


       private String message="Hi I am a Component Annotation 1";


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
