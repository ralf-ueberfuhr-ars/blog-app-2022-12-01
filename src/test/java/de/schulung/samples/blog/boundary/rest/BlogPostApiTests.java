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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    // TODO GET /posts/5 + kein Post mit ID=5  -> 404
    // TODO GET /posts/5 -> 200 + JSON (mit id=5)
    // TODO GET /posts/gelbeKatze -> 400
    // TODO DELETE /posts/5 + AUTHOR + kein Post mit ID=5  -> 404
    // TODO DELETE /posts/5 + kein AUTHOR  -> 403
    // TODO DELETE /posts/5 + AUTHOR, aber nicht Autor des BlogPost  -> 403
    // TODO DELETE /posts/5 + AUTHOR + Autor des BlogPosts -> 204 + service.removePost(...)
    // TODO DELETE /posts/5 + AUTHOR + BlogPosts hat keinen Autor -> 204 + service.removePost(...)
    // TODO weitere Testfälle zu den HashTags ...
}
