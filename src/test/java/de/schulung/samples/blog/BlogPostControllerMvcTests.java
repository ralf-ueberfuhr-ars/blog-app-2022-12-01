package de.schulung.samples.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogPostControllerMvcTests {

    @MockBean
    BlogPostService service;
    @Autowired
    MockMvc mvc;

    /*
     * Request:
     *  - /posts/findall
     *  - GET
     * Response:
     *  - StatusCode: 200
     *  - (timestamp im JSON?)
     */

    @Test
    void shouldReturn200OkOnFindAll() throws Exception {
        when(service.findPosts()).thenReturn(Collections.emptyList());

        mvc.perform(get("/posts/findall"))
            .andExpect(status().isOk());

        verify(service).findPosts();

    }

    @Test
    void shouldIncludeTimestampToJsonOnFindAll() throws Exception {
        when(service.findPosts()).thenReturn(List.of(
          BlogPost.builder()
            .id(1L)
            .title("test")
            .content("test")
            .timestamp(LocalDateTime.of(2022, Month.JANUARY, 10, 10,0,0))
            .build()
        ));

        mvc.perform(get("/posts/findall"))
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("*.timestamp").exists());

        verify(service).findPosts();

    }

}
