package java_project.A_xml_based_config.b_alarmexample;

public class SoundAlarm  implements  IAlarm{
    @Override
    public void activate() {
        System.out.println("Sound Alarm activated");
    }

    @Override
    public void deactivate() {
        System.out.println("Sound Alarm deactivated");
    }
}
