package com.learn.spring.l19_web_mvc;

import java.io.File;
import java.nio.file.Files;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * What Spring Boot does for you in one line (SpringApplication.run), done by hand:
 * 1. start a servlet container (Tomcat)
 * 2. create a web ApplicationContext from WebConfig
 * 3. register Spring's DispatcherServlet (the "front controller") for every URL
 */
public final class EmbeddedTomcatServer {

    private EmbeddedTomcatServer() {
    }

    public static Tomcat start(int port) throws Exception {
        Tomcat tomcat = new Tomcat();
        File baseDir = Files.createTempDirectory("tomcat").toFile();
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        tomcat.setPort(port);
        tomcat.getConnector();   // creates the default HTTP connector

        Context servletContext = tomcat.addContext("", baseDir.getAbsolutePath());

        var springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebConfig.class);

        // Every request goes to the DispatcherServlet, which routes it to the matching @Controller method
        var dispatcher = new DispatcherServlet(springContext);
        Tomcat.addServlet(servletContext, "dispatcher", dispatcher).setLoadOnStartup(1);
        servletContext.addServletMappingDecoded("/", "dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new IllegalStateException("Could not start Tomcat on port " + port, e);
        }
        return tomcat;
    }
}
