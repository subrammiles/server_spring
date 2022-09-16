package java_project.B_basics.a_agetovote;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:voters_age.properties")
public class AgeToVoteConfig {

    @Value("${alarmType}")
    private int alarmType;

    public int  getAlaryType(){
        return alarmType;
    }

}
