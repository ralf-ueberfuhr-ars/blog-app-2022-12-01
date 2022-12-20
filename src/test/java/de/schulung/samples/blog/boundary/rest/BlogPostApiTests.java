package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.boundary.config.TestSecurityConfiguration;
import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test gegen gesamte Anwendung inkl. Test-Datenbank
 */
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
/*
 * Test der Boundary mit gemockter Domain
 */
@WebMvcTest(controllers = BlogPostRestController.class)
@ComponentScan(basePackages = "de.schulung.samples.blog.shared.config")
@Import(TestSecurityConfiguration.class)
@WithMockUser(roles = "READER")
class BlogPostApiTests {

    @Autowired
    MockMvc mvc;
    @MockBean
    BlogPostService service;

    // GET /posts -> 200 OK Statuscode + [] (JSON)
    @Test
    void shouldReturn200OnReadAllPosts() throws Exception {
        mvc.perform(
            get("/api/v1/posts")
          )
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    }

    // GET /posts -> 200 OK + BlogPost im JSON
    @Test
    void shouldReturn200AndBlogPostOnRealAllPosts() throws Exception {
        BlogPost post = BlogPost.builder()
          .id(1L)
          .title("test")
          .content("testtesttest")
          .timestamp(LocalDateTime.now())
          .build();
        when(service.findPosts()).thenReturn(List.of(post));

        mvc.perform(
            get("/api/v1/posts")
          )
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].content").value("testtesttest"));

    }

    @Test
    @WithMockUser(roles = {})
    void shouldReturn403ForbiddenIfMissingReaderRoleOnReadAllPosts() throws Exception {
        mvc.perform(
            get("/api/v1/posts")
          )
          .andExpect(status().isForbidden());
    }

    // POST api/v1/posts mit Post als JSON -> 201 Created + JSON + Location Header
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn201CreatedOnCreatePost() throws Exception {

        // instrument service to set id when addPost is invoked
        doAnswer(invocation -> {
            BlogPost postToCreate = invocation.getArgument(0);
            postToCreate.setId(1L);
            return null;
        }).when(service).addPost(any());

        mvc.perform(
            post("/api/v1/posts")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"title\": \"test\",\"content\": \"testtesttest\"}")
          )
          .andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          //.andExpect(jsonPath("id").exists())
          //.andExpect(jsonPath("timestamp").exists())
          .andExpect(jsonPath("author").value("test-user"));

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(service).addPost(captor.capture());
        BlogPost blogPostThatWasAdded = captor.getValue();
        assertThat(blogPostThatWasAdded)
          .extracting(BlogPost::getAuthor)
          .isEqualTo("test-user");
    }

    // GET /posts/5 -> 200 + JSON (mit id=5)
    @Test
    void shouldReturn200OkOnReadExistingPost() throws Exception {
        BlogPost post = BlogPost.builder()
          .id(5L)
          .title("test")
          .content("testtesttest")
          .timestamp(LocalDateTime.now())
          .build();
        when(service.findPostById(5L)).thenReturn(Optional.of(post));
        mvc.perform(
            get("/api/v1/posts/5")
          )
          .andExpect(status().isOk())
          .andExpect(jsonPath("id").value(5L));
    }

    // GET /posts/5 + kein Post mit ID=5  -> 404
    @Test
    void shouldReturn404NotFoundOnReadNonExistingPost() throws Exception {
        when(service.findPostById(5L)).thenReturn(Optional.empty());
        mvc.perform(
            get("/api/v1/posts/5")
          )
          .andExpect(status().isNotFound());
    }

    // GET /posts/gelbeKatze -> 400
    @Test
    void shouldReturn400BadRequestOnReadPostWithInvalidId() throws Exception {
        mvc.perform(
            get("/api/v1/posts/gelbeKatze")
          )
          .andExpect(status().isBadRequest());
    }

    // DELETE /posts/gelbeKatze -> 400
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn400BadRequestOnDeletePostWithInvalidId() throws Exception {
        mvc.perform(
            delete("/api/v1/posts/gelbeKatze")
          )
          .andExpect(status().isBadRequest());
    }

    // DELETE /posts/5 + kein AUTHOR  -> 403
    @Test
    void shouldReturn403ForbiddenOnMissingAuthorRoleOnDeletePost() throws Exception {
        mvc.perform(
            delete("/api/v1/posts/5")
          )
          .andExpect(status().isForbidden());
    }

    // DELETE /posts/5 + AUTHOR + kein Post mit ID=5  -> 404
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn404NotFoundOnDeleteNonExistingPost() throws Exception {
        when(service.findPostById(5L)).thenReturn(Optional.empty());
        mvc.perform(
            delete("/api/v1/posts/5")
          )
          .andExpect(status().isNotFound());
    }

    // DELETE /posts/5 + AUTHOR, aber nicht Autor des BlogPost  -> 403
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn403ForbiddenOnDeleteBlogPostOfAnotherAuthor() throws Exception {
        BlogPost post = BlogPost.builder()
          .id(5L)
          .title("test")
          .content("testtesttest")
          .timestamp(LocalDateTime.now())
          .author("another-user")
          .build();
        when(service.findPostById(5L)).thenReturn(Optional.of(post));
        mvc.perform(
            delete("/api/v1/posts/5")
          )
          .andExpect(status().isForbidden());
    }

    // DELETE /posts/5 + AUTHOR + Autor des BlogPosts -> 204 + service.removePost(...)
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn2xxSuccessfulOnDeleteOwnBlogPost() throws Exception {
        BlogPost post = BlogPost.builder()
          .id(5L)
          .title("test")
          .content("testtesttest")
          .timestamp(LocalDateTime.now())
          .author("test-user")
          .build();
        when(service.findPostById(5L)).thenReturn(Optional.of(post));
        mvc.perform(
            delete("/api/v1/posts/5")
          )
          .andExpect(status().is2xxSuccessful());
        verify(service).removePost(5L);
    }

    // DELETE /posts/5 + AUTHOR + BlogPosts hat keinen Autor -> 204 + service.removePost(...)
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn2xxSuccessfulOnDeleteAnonymousBlogPost() throws Exception {
        BlogPost post = BlogPost.builder()
          .id(5L)
          .title("test")
          .content("testtesttest")
          .timestamp(LocalDateTime.now())
          // .author(null) --> default
          .build();
        when(service.findPostById(5L)).thenReturn(Optional.of(post));
        mvc.perform(
            delete("/api/v1/posts/5")
          )
          .andExpect(status().is2xxSuccessful());
        verify(service).removePost(5L);
    }
    // TODO weitere Testfälle zu den HashTags ...
}
