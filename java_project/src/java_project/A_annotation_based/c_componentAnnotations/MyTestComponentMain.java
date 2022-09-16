package java_project.A_annotation_based.c_componentAnnotations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/*
1.We will create few components and add @Component("") tage to that .
2.System need to load that components.For that define the components path in the testAnnotationComponentScanConfig
* */
public class MyTestComponentMain {


    public static  void testComponentWithWithAnnotations() {

        ApplicationContext context=new FileSystemXmlApplicationContext("classpath:testAnnotationComponentScanConfig.xml");


        MyTestAnnotationComponent  myTestAnnotationComponent=context.getBean("myTestAnnotationComponent",MyTestAnnotationComponent.class);

        System.out.println(myTestAnnotationComponent.getMessage());


        MyTestAnnotationComponent2  myTestAnnotationComponent2=context.getBean("myTestAnnotationComponent2",MyTestAnnotationComponent2.class);

        System.out.println(myTestAnnotationComponent2.getMessage());

        /*We did't specified the component name explicetly.It will take the defau;t name myTestAnnotationComponent3 */

        MyTestAnnotationComponent3  myTestAnnotationComponent3=context.getBean("myTestAnnotationComponent3",MyTestAnnotationComponent3.class);

        System.out.println(myTestAnnotationComponent3.getMessage());

    }

    public static  void  main(){

        testComponentWithWithAnnotations();
    }

}
