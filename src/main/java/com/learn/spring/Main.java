package com.learn.spring;

import java.util.LinkedHashMap;
import java.util.Map;

import com.learn.spring.l01_without_spring.WithoutSpringDemo;
import com.learn.spring.l02_xml_config.XmlConfigDemo;
import com.learn.spring.l03_java_config.JavaConfigDemo;
import com.learn.spring.l04_component_scan.ComponentScanDemo;
import com.learn.spring.l05_autowiring_choices.AutowiringDemo;
import com.learn.spring.l06_bean_scopes.ScopesDemo;
import com.learn.spring.l07_bean_lifecycle.LifecycleDemo;
import com.learn.spring.l08_externalized_config.ExternalizedConfigDemo;
import com.learn.spring.l09_profiles_conditions.ProfilesDemo;
import com.learn.spring.l10_events.EventsDemo;
import com.learn.spring.l11_aop.AopDemo;
import com.learn.spring.l12_validation.ValidationDemo;
import com.learn.spring.l13_async_scheduling.AsyncSchedulingDemo;
import com.learn.spring.l14_caching.CachingDemo;
import com.learn.spring.l15_jdbc.JdbcDemo;
import com.learn.spring.l16_transactions.TransactionsDemo;
import com.learn.spring.l17_jpa_hibernate.JpaDemo;
import com.learn.spring.l18_spring_data_jpa.SpringDataJpaDemo;
import com.learn.spring.l19_web_mvc.WebMvcDemo;

/**
 * Runs one lesson's demo:  mvn -q compile exec:java -Dexec.args=05
 * Without an argument it lists the lessons.
 */
public class Main {

    interface Demo {
        void run(String[] args) throws Exception;
    }

    record Lesson(String title, Demo demo) {
    }

    private static final Map<String, Lesson> LESSONS = new LinkedHashMap<>();

    static {
        LESSONS.put("01", new Lesson("Life without Spring: why DI exists", WithoutSpringDemo::main));
        LESSONS.put("02", new Lesson("IoC container with XML configuration", XmlConfigDemo::main));
        LESSONS.put("03", new Lesson("Java configuration: @Configuration, @Bean", JavaConfigDemo::main));
        LESSONS.put("04", new Lesson("Component scanning and @Autowired", ComponentScanDemo::main));
        LESSONS.put("05", new Lesson("Many beans of one type: @Primary, @Qualifier, List/Map", AutowiringDemo::main));
        LESSONS.put("06", new Lesson("Bean scopes and @Lazy", ScopesDemo::main));
        LESSONS.put("07", new Lesson("Bean lifecycle and post-processors", LifecycleDemo::main));
        LESSONS.put("08", new Lesson("Properties, Environment, SpEL, resources, i18n", ExternalizedConfigDemo::main));
        LESSONS.put("09", new Lesson("Profiles and @Conditional", ProfilesDemo::main));
        LESSONS.put("10", new Lesson("Application events", EventsDemo::main));
        LESSONS.put("11", new Lesson("AOP: aspects, pointcuts, advice", AopDemo::main));
        LESSONS.put("12", new Lesson("Bean Validation", ValidationDemo::main));
        LESSONS.put("13", new Lesson("@Async and @Scheduled", AsyncSchedulingDemo::main));
        LESSONS.put("14", new Lesson("Caching", CachingDemo::main));
        LESSONS.put("15", new Lesson("Spring JDBC: JdbcTemplate, JdbcClient", JdbcDemo::main));
        LESSONS.put("16", new Lesson("Transactions", TransactionsDemo::main));
        LESSONS.put("17", new Lesson("JPA and Hibernate", JpaDemo::main));
        LESSONS.put("18", new Lesson("Spring Data JPA", SpringDataJpaDemo::main));
        LESSONS.put("19", new Lesson("REST APIs with Spring MVC + embedded Tomcat", WebMvcDemo::main));
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || !LESSONS.containsKey(normalize(args[0]))) {
            System.out.println("Usage: mvn -q compile exec:java -Dexec.args=<lesson>\n");
            LESSONS.forEach((n, l) -> System.out.println("  " + n + "  " + l.title()));
            System.out.println("  20  Testing Spring applications -> run: mvn test");
            return;
        }
        String number = normalize(args[0]);
        Lesson lesson = LESSONS.get(number);
        System.out.println("=== Lesson " + number + ": " + lesson.title() + " ===\n");
        lesson.demo().run(new String[0]);
    }

    private static String normalize(String arg) {
        return arg.length() == 1 ? "0" + arg : arg;
    }
}
