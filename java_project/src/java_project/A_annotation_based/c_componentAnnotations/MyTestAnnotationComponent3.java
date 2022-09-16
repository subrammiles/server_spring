package java_project.A_annotation_based.c_componentAnnotations;


import org.springframework.stereotype.Component;

/*We did't specified the component name explicetly.It will take the defau;t name myTestAnnotationComponent3 */
@Component("")
public class MyTestAnnotationComponent3 {


    private String message="Hi I am a Component Annotation 3";


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
