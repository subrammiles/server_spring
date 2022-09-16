package java_project.A_xml_based_config.b_alarmexample;

public class Door {
    IAlarm alarm;

    public  Door() {
       //Default constracture is must to create a bean
    }

      public  Door(IAlarm alarm) {
        this.alarm=alarm;
    }
    public void  setAlarm(IAlarm alarm) {
        this.alarm=alarm;
    }

    public void open() {
        alarm.activate();
    }
    public void close() {
        alarm.deactivate();
    }

}
