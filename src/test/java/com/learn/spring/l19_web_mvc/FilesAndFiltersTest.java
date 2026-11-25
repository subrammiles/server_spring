package com.learn.spring.l19_web_mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/** MockMvc has no Tomcat, so servlet filters must be added to it explicitly with addFilters(...). */
@SpringJUnitWebConfig(WebConfig.class)
class FilesAndFiltersTest {

    MockMvc mvc;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext context) {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CommonHeadersFilter()).build();
    }

    @Test
    void uploadThenDownloadAFile() throws Exception {
        var file = new MockMultipartFile("file", "notes.txt", "text/plain", "remember the milk".getBytes());

        mvc.perform(multipart("/api/files").file(file))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/files/notes.txt"))
                .andExpect(jsonPath("$.size").value(17));

        mvc.perform(get("/api/files/notes.txt"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/plain"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"notes.txt\""))
                .andExpect(content().string("remember the milk"));
    }

    @Test
    void clientFileNamesCannotEscapeIntoPaths() throws Exception {
        var file = new MockMultipartFile("file", "../../etc/passwd", "text/plain", "x".getBytes());

        mvc.perform(multipart("/api/files").file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("passwd"));
    }

    @Test
    void emptyUploadIs400AndMissingFileIs404() throws Exception {
        mvc.perform(multipart("/api/files").file(new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0])))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/files/nope.txt")).andExpect(status().isNotFound());
    }

    @Test
    void filterRunsForEveryRequestButTheInterceptorOnlyForMatchedHandlers() throws Exception {
        mvc.perform(get("/api/tasks"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().exists("X-Request-Id"));

        mvc.perform(get("/no-such-url"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))   // the filter ran
                .andExpect(header().doesNotExist("X-Request-Id"));                 // no handler, no interceptor
    }
}
