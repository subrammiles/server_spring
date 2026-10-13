package com.learn.spring.l19_web_mvc;

import org.apache.catalina.startup.Tomcat;

public class WebMvcDemo {

    public static void main(String[] args) throws Exception {
        int port = Integer.getInteger("port", 8080);
        Tomcat tomcat = EmbeddedTomcatServer.start(port);
        String base = "http://localhost:" + port;
        System.out.println("""

                Server running on %1$s  (Ctrl+C to stop). Try:

                  curl %1$s/hello?name=Subbu
                  curl %1$s/users/42/greeting
                  curl -i -X POST %1$s/api/tasks -H 'Content-Type: application/json' -d '{"title":"Learn Spring MVC"}'
                  curl %1$s/api/tasks
                  curl %1$s/api/tasks?done=false
                  curl -i -X PUT %1$s/api/tasks/1 -H 'Content-Type: application/json' -d '{"title":"Learn Spring MVC","done":true}'
                  curl -i %1$s/api/tasks/99                      (404 ProblemDetail)
                  curl -i -X POST %1$s/api/tasks -H 'Content-Type: application/json' -d '{"title":""}'   (400 validation)
                  curl -i -X DELETE %1$s/api/tasks/1
                """.formatted(base));
        tomcat.getServer().await();
    }
}
