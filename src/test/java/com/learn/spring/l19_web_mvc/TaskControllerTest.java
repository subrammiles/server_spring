package com.learn.spring.l19_web_mvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * MockMvc runs the full Spring MVC stack (DispatcherServlet, JSON, validation, advice,
 * interceptors) WITHOUT starting Tomcat or opening a port: fast and reliable.
 */
@SpringJUnitWebConfig(WebConfig.class)
class TaskControllerTest {

    MockMvc mvc;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext context) {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createReturns201WithLocationAndBody() throws Exception {
        mvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Write tests"}"""))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/api/tasks/")))
                .andExpect(header().exists("X-Request-Id"))
                .andExpect(jsonPath("$.title").value("Write tests"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void listCanBeFiltered() throws Exception {
        mvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                {"title": "Finished task", "done": true}"""));

        mvc.perform(get("/api/tasks").param("done", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItem("Finished task")));
    }

    @Test
    void invalidBodyReturns400ProblemWithFieldErrors() throws Exception {
        mvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": ""}"""))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", "application/problem+json"))
                .andExpect(jsonPath("$.errors.title").value("must not be blank"));
    }

    @Test
    void unknownTaskReturns404Problem() throws Exception {
        mvc.perform(get("/api/tasks/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Task 9999 not found"));
        mvc.perform(delete("/api/tasks/9999")).andExpect(status().isNotFound());
    }

    @Test
    void requestParamsPathVariablesAndHeaders() throws Exception {
        mvc.perform(get("/hello").param("name", "Subbu"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Subbu!"));
        mvc.perform(get("/users/7/greeting").header("Accept-Language", "te"))
                .andExpect(jsonPath("$.userId").value(7))
                .andExpect(jsonPath("$.language").value("te"));
    }
}
