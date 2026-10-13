package com.learn.spring.l20_testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.context.WebApplicationContext;

import com.learn.spring.l19_web_mvc.WebConfig;

/**
 * TECHNIQUE 4: web tests with MockMvcTester, the AssertJ-style MockMvc API (Spring 6.2+).
 * Compare with the classic MockMvc style in l19_web_mvc/TaskControllerTest.
 */
@SpringJUnitWebConfig(WebConfig.class)
class MockMvcTesterTest {

    private final MockMvcTester mvc;

    MockMvcTesterTest(WebApplicationContext context) {   // constructor injection works in tests too
        this.mvc = MockMvcTester.from(context);
    }

    @Test
    void createThenFetch() {
        var created = mvc.post().uri("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Try MockMvcTester\"}")
                .exchange();

        assertThat(created).hasStatus(HttpStatus.CREATED);
        assertThat(created).bodyJson().extractingPath("$.title").isEqualTo("Try MockMvcTester");

        String location = created.getResponse().getHeader("Location");
        assertThat(mvc.get().uri(location)).hasStatusOk()
                .bodyJson().extractingPath("$.done").isEqualTo(false);
    }

    @Test
    void notFound() {
        assertThat(mvc.get().uri("/api/tasks/12345")).hasStatus(HttpStatus.NOT_FOUND);
    }
}
