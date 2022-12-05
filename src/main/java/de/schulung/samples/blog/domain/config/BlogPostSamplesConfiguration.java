package de.schulung.samples.blog.domain.config;

import de.schulung.samples.blog.domain.BlogPost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BlogPostSamplesConfiguration {

    @Bean
    BlogPostProvider sample1() {
        return BlogPost.builder()
          .title("Mein erster Post")
          .content("Lorem ipsum...")
          ::build;
    }

    @Bean
    BlogPostProvider sample2() {
        return BlogPost.builder()
          .title("Mein zweiter Post")
          .content("Lorem ipsum...")
          ::build;
    }

    @Bean
    @Profile("dev")
    BlogPostProvider sample3() {
        return BlogPost.builder()
          .title("DEV Profile ist aktiviert!")
          .content("Lorem ipsum...")
          ::build;
    }

}
