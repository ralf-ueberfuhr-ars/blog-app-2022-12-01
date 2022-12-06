package de.schulung.samples.blog.boundary.config;

import de.schulung.samples.blog.domain.BlogPostService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test-mockmvc")
public class BoundaryMocksConfiguration {

    @Bean
    BlogPostService blogPostServiceMock() {
        return Mockito.mock(BlogPostService.class);
    }

}
