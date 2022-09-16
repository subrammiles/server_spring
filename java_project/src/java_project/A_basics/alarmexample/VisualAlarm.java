package java_project.A_basics.alarmexample;

public class VisualAlarm implements IAlarm{
    @Override
    public void activate() {

        System.out.println("Visual Alarm activated");

    }

    @Override
    public void deactivate() {
        System.out.println("Visual Alarm deactivated");
    }
}
