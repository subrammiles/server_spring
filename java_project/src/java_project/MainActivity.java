package java_project;


import java_project.A_AutoWiring.autowiring.AutoWiredBasicEngine;
import java_project.A_di_and_ioc.DIByConstructor.DiByConstrutorEngine;
import java_project.A_java_based.a_simple.SimpleJavaBasesConfigEngine;
import java_project.A_java_based.b_multipulconfiguration.MultipulJavaConfigEngine;
import java_project.A_java_based.c_InjectingBeanDependencies.InjectingBeanEngine;
import java_project.A_java_based.d_importAnnotation.ImportAnnotationEngine;
import java_project.A_java_based.e_LifecycleCallbacks.LifeCycleCallBackEngine;
import java_project.C_events.a_applicationcontextevents.ApplicationEventTestEngine;
import java_project.C_events.b_custome.CustomeEventEngine;
import java_project.alarmexample.autowiring.Door3;
import java_project.alarmexample.configbasedbeans.AlarmBeansConfigFile;
import java_project.alarmexample.configbasedbeans.Door5;
import java_project.alarmexample.configbasedbeans.SoundAlarm5;
import java_project.alarmexample.configbasedbeans.VisualAlarm5;
import java_project.alarmexample.qualifier.Door4;
import java_project.config.ComponentScanConfig;
import java_project.entityexample.UserEntity;
import java_project.hibernate.a_test1.HibernateTest1Engine;
import java_project.repositoryexample.Student;
import java_project.repositoryexample.StudentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity  extends  Throwable{




	public static  void alarmExampleTestAutoWiring() {
		ApplicationContext context=new FileSystemXmlApplicationContext("classpath:componentscan.xml");

		Door3 door=context.getBean("door3",Door3.class);
		door.open();
		door.close();
		/*We added only VisualAlarm3  to beans.We didn't added SoundAlarm3 to beans.Even thought SoundAlarm3 extedns Alarm3,we did't added to beans.*/
		/*So in output you will see Visual Alarm only.Not sound alarm*/
		/*If you add  SoundAlarm3 also to bean you will get the following errror*/
		/*Error:available: expected single matching bean but found 2*/
		/*You can fix this error,that when Multipul classes extends a Interface and it will trow an error*/

	}

	public static  void alarmExampleTestAutoWiringWithQalifier(){
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ComponentScanConfig.class);

		Door4 door=context.getBean("door4",Door4.class);
		door.open();
		door.close();

	}


	public static  void alarmExampleWithJavaConfigBasedBeans() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AlarmBeansConfigFile.class);


		SoundAlarm5 soundAlarm=context.getBean("soundAlarm",SoundAlarm5.class);
		VisualAlarm5 visualAlarm=context.getBean("visualAlarm",VisualAlarm5.class);

		Door5 door=new Door5();
		door.setAlarm(visualAlarm);
		//door.setAlarm(soundAlarm);
		door.open();
		door.close();


	}

	public static  void alarmExamplePropertietFile() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AlarmBeansConfigFile.class);
		/*Propertes file testing*/
		Door5 door=context.getBean("door",Door5.class);
		door.open();
		door.close();
	}




	public static  void springConfigWithJavaCode(){
		ApplicationContext context=new FileSystemXmlApplicationContext("classpath:componentscan.xml");

		Door4 door=context.getBean("door4",Door4.class);
		door.open();
		door.close();

	}









	public static void demoRepository() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("java_project.repositoryexample");
		context.refresh();

		StudentRepository repository = context.getBean(StudentRepository.class);
		// testing the store method
		repository.save(new Student(1L, "Anshul", 25));
		repository.save(new Student(2L, "Mayank", 23));
		// testing the retrieve method
		Student student = repository.findStudentById(1L);
		System.out.println(student);
		// close the spring context
		context.close();
	}


	public static void jdbcTest(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/mytestdb","root","**********");

			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from users");
			while(rs.next())
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			con.close();
		}catch(Exception e){ System.out.println(e);}
	}

	public static void userEntity() {
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("user_details");
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();

		UserEntity s1=new UserEntity();
		s1.setUserId(1);
		s1.setEmail("user1@gmail.com");
		s1.setPhone("9123456789");
		UserEntity s2=new UserEntity();
		s1.setUserId(2);
		s1.setEmail("user2@gmail.com");
		s1.setPhone("8123456789");


		em.persist(s1);
		em.persist(s2);

		em.getTransaction().commit();

		emf.close();
		em.close();



	}




	public void hybernateExample() {
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//
//		//Add new Employee object
//		EmployeeEntity emp = new EmployeeEntity();
//		emp.setEmail("demo-user@mail.com");
//		emp.setFirstName("demo");
//		emp.setLastName("user");
//
//		session.save(emp);
//
//		session.getTransaction().commit();
//		HibernateUtil.shutdown();
	}
	public static void main(String[] args) {
//		HelloWorldEngine1.main();
//		AlaramExampleEngine1.main();
//		AgeToVoteEngine.main();
//		MyTestComponentMain.main();
//		SimpleJavaBasesConfigEngine.main();
//		MultipulJavaConfigEngine.main();
//		InjectingBeanEngine.main();
//		ImportAnnotationEngine.main();
//		LifeCycleCallBackEngine.main();
//		ApplicationEventTestEngine.main();
//		CustomeEventEngine.main();
//		DiByConstrutorEngine.main();
//		AutoWiredBasicEngine.main();
		HibernateTest1Engine.main();





		//helloWorld();
		//alarmExampleTestWithXMLConfig();

		/**/
		//alarmExampleTestWithAnnotations();
		//alarmExampleTestAutoWiring();
		//alarmExampleTestAutoWiringWithQalifier();
		//springConfigWithJavaCode();
		//alarmExampleWithJavaConfigBasedBeans();
		//alarmExamplePropertietFile();
		//demoRepository();
		//userEntity();
	}
}
