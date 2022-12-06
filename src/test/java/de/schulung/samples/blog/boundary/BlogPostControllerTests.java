package de.schulung.samples.blog.boundary;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogPostControllerTests {

    @Mock
    BlogPostService service;
    @InjectMocks
    BlogPostController controller;

    @Test
    void shouldInvokeServiceToFindPosts() {
        // BlogPostService service = Mockito.mock(BlogPostService.class);
        // BlogPostController controller = new BlogPostController(service);
        when(service.findPosts()).thenReturn(Collections.emptyList());

        Collection<BlogPost> result = controller.getBlogPosts();

        assertThat(result).isEmpty();
        verify(service).findPosts();

    }

}
