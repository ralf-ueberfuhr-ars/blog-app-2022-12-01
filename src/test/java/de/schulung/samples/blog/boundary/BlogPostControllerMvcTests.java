package de.schulung.samples.blog.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "READER")
class BlogPostControllerMvcTests {

    @MockBean
    BlogPostService service;
    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setup() {
        // clean invocations during setup
        Mockito.reset(service);
    }

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
            .timestamp(LocalDateTime.of(2022, Month.JANUARY, 10, 10, 0, 0))
            .build()
        ));

        mvc.perform(get("/posts/findall"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("*.timestamp").exists());

        verify(service).findPosts();

    }

    @Test
    void shouldReturn200OkOnFind() throws Exception {
        when(service.findPostById(1L)).thenReturn(
          Optional.of(
            BlogPost.builder()
              .id(1L)
              .title("test")
              .content("test")
              .timestamp(LocalDateTime.of(2022, Month.JANUARY, 10, 10, 0, 0))
              .build()
          )
        );

        mvc.perform(get("/posts/find?id=1"))
          .andExpect(status().isOk());

        verify(service).findPostById(eq(1L));

    }

    @Test
    void shouldReturn400BadRequestOnMissingParameter() throws Exception {
        mvc.perform(get("/posts/find"))
          .andExpect(status().isBadRequest());
        verify(service, never()).findPostById(anyLong());
    }

    @Test
    void shouldReturn400BadRequestOnInvalidParameter() throws Exception {
        mvc.perform(get("/posts/find?id=gelbekatze"))
          .andExpect(status().isBadRequest());
        verify(service, never()).findPostById(anyLong());
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnCorrectJSON() throws Exception {
        BlogPost originalBlogPost = BlogPost.builder()
          .id(1L)
          .title("test")
          .content("test")
          .timestamp(LocalDateTime.of(2022, Month.JANUARY, 10, 10, 0, 0))
          .build();
        when(service.findPostById(1L)).thenReturn(Optional.of(originalBlogPost));

        String body = mvc.perform(get("/posts/find?id=1"))
          .andExpect(status().isOk())
          .andReturn().getResponse().getContentAsString();
        BlogPost resultBlogPost = objectMapper.readValue(body, BlogPost.class);

        assertThat(resultBlogPost).isEqualTo(originalBlogPost);
        verify(service).findPostById(eq(1L));

    }

    @Test
    void shouldReturn401OnCreateBlogPost() throws Exception {
        mvc.perform(
          post("/posts/create")
            .param("title", "title")
            .param("content", "titletitle")
        ).andExpect(status().isForbidden());
        verify(service, never()).addPost(any());
    }

    @Test
    @WithMockUser(value = "testuser", roles = "AUTHOR")
    void shouldReturn200OnCreateBlogPost() throws Exception {
        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        mvc.perform(
          post("/posts/create")
            .param("title", "title")
            .param("content", "titletitle")
        ).andExpect(status().is3xxRedirection());
        verify(service).addPost(captor.capture());
        assertThat(captor.getValue().getAuthor()).isEqualTo("testuser");
    }

}
