package java_project.alarmexample.configbasedbeans;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:voters_age.properties")
public class AlarmBeansConfigFile {


    @Value("${alarmType}")
    private int alarmType;

    @Bean
    public SoundAlarm5 soundAlarm(){
        SoundAlarm5 soundAlarm=new SoundAlarm5();
        return soundAlarm;
    }

    @Bean
    public VisualAlarm5 visualAlarm(){
        VisualAlarm5 visualAlarm=new VisualAlarm5();
        return visualAlarm;
    }

//    @Bean Door5 door(){
//           Door5 door=new Door5();
//           if(alarmType==1){
//               door.setAlarm(visualAlarm());
//           }else{
//               door.setAlarm(soundAlarm());
//           }
//           return door;
//    }

}
