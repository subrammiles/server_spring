package java_project.A_annotation_based.c_componentAnnotations;


import org.springframework.stereotype.Component;

@Component("myTestAnnotationComponent2")
public class MyTestAnnotationComponent2 {


    private String message="Hi I am a Component Annotation 2";


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
