package java_project.A_basics.alarmexample;

import java_project.A_basics.a_helloworld.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AlaramExampleEngine1 {

    public static void main(){
        ApplicationContext context=new FileSystemXmlApplicationContext("classpath:alarmbeans.xml");
        /*We loaded alarmbeans xml*/
        testVisualAlarmBeanWithXMLConfig(context);
        testSoundAlarmBeanWithXMLConfig(context);
        testDoorBean(context);
    }


    public static  void testVisualAlarmBeanWithXMLConfig(ApplicationContext context) {
        System.out.println("<<<<Visual Alarm Bean Test>>>>>");
        /*Now get the Alarm Bean from contest*/
        VisualAlarm visualAlarm=context.getBean("visualAlarmBean",VisualAlarm.class);
        Door door=new Door(visualAlarm);
        door.open();
        door.close();
    }

    public static  void testSoundAlarmBeanWithXMLConfig(ApplicationContext context) {
        System.out.println("<<<<Sound Alarm Bean Test>>>>>");
        /*Now get the Alarm Bean from contest*/
        SoundAlarm soundAlarm=context.getBean("soundAlarmBean",SoundAlarm.class);
        Door door=new Door(soundAlarm);
        door.open();
        door.close();
    }
        public static  void testDoorBean(ApplicationContext context) {
            System.out.println("<<<<Door  Bean Test>>>>>");
            /*Now get the Dooe  Bean*/
            /*We are passing Sound Alarm to Door*/
            Door door=context.getBean("doorBeanWithSoundAlarm", Door.class);
            door.open();
            door.close();
            /*We are passing Visual Alarm to Door*/
            Door door2=context.getBean("doorBeanWithVisualAlarm", Door.class);
            door2.open();
            door2.close();
        }

}
