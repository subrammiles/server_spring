package java_project.alarmexample.configbasedbeans;



public class VisualAlarm5 implements Alarm5 {

    @Override
    public void activate() {
        System.out.println("Visual Alarm activated");
    }

    @Override
    public void deactivate() {
        System.out.println("Visual Alarm deactivated");
    }
}