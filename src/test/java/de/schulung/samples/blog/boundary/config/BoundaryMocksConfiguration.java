package de.schulung.samples.blog.boundary.config;

import de.schulung.samples.blog.domain.BlogPostService;
import de.schulung.samples.blog.domain.HashTagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test-mockmvc")
public class BoundaryMocksConfiguration {

    @Bean
    BlogPostService blogPostServiceMock() {
        return mock(BlogPostService.class);
    }

    @Bean
    HashTagService hashTagService() {
        return mock(HashTagService.class);
    }

}
