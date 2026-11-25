package com.learn.spring.l19_web_mvc;

import java.io.File;
import java.nio.file.Files;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.MultipartConfigElement;

/**
 * What Spring Boot does for you in one line (SpringApplication.run), done by hand:
 * 1. start a servlet container (Tomcat)
 * 2. create a web ApplicationContext from WebConfig
 * 3. register Spring's DispatcherServlet (the "front controller") for every URL
 * 4. register a servlet Filter in front of it
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
        // Tomcat gives each web app its own class loader. Make it see OUR classes, otherwise component
        // scanning finds no controllers when launched through `mvn exec:java` (every URL is a 404).
        servletContext.setParentClassLoader(EmbeddedTomcatServer.class.getClassLoader());

        var springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebConfig.class);

        // Every request goes to the DispatcherServlet, which routes it to the matching @Controller method
        var dispatcher = new DispatcherServlet(springContext);
        var wrapper = Tomcat.addServlet(servletContext, "dispatcher", dispatcher);
        wrapper.setLoadOnStartup(1);
        // Enables multipart (file upload) parsing: max 1 MB per file, 2 MB per request.
        // Larger uploads are rejected with 413 Payload Too Large (ResponseEntityExceptionHandler).
        wrapper.setMultipartConfigElement(new MultipartConfigElement(null, 1_048_576, 2_097_152, 0));
        servletContext.addServletMappingDecoded("/", "dispatcher");

        // A servlet Filter, registered with the CONTAINER (not Spring MVC) for every URL
        var filterDef = new FilterDef();
        filterDef.setFilterName("commonHeaders");
        filterDef.setFilter(new CommonHeadersFilter());
        servletContext.addFilterDef(filterDef);
        var filterMap = new FilterMap();
        filterMap.setFilterName("commonHeaders");
        filterMap.addURLPattern("/*");
        servletContext.addFilterMap(filterMap);

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new IllegalStateException("Could not start Tomcat on port " + port, e);
        }
        return tomcat;
    }
}
