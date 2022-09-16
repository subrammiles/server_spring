package java_project.B_basics.a_agetovote;


import java_project.alarmexample.Door;
import java_project.alarmexample.qualifier.Door4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AgeToVoteEngine {




    public static void main(){
        System.out.println("Age to vote is ....");
        ApplicationContext context=new FileSystemXmlApplicationContext("classpath:AgeToVoteConfigBean.xml");
        AgeToVoteConfig ageToVoteConfig=context.getBean("ageToVoteConfig",AgeToVoteConfig.class);
        System.out.println(ageToVoteConfig.getAlaryType());
    }
}
