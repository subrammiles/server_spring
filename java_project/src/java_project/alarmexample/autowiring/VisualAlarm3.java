package java_project.alarmexample.autowiring;

import java_project.alarmexample.withAnnotation.Alarm2;
import org.springframework.stereotype.Component;



@Component()
public class VisualAlarm3 implements Alarm3{

    @Override
    public void activate() {
        System.out.println("Visual Alarm activated");
    }

    @Override
    public void deactivate() {
        System.out.println("Visual Alarm activated");
    }
}