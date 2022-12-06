package de.schulung.samples.blog.domain.config;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@Slf4j
public class InMemoryBlogPostSinkConfiguration {

    @Bean
    @ConditionalOnMissingBean(BlogPostSink.class)
    BlogPostSink inMemoryBlogPostSink() {
        log.info("Creating in-memory blog post sink.");
        return new InMemoryBlogPostSink();
    }

    private static class InMemoryBlogPostSink implements BlogPostSink {

        private final Map<Long, BlogPost> posts = new HashMap<>();

        private static long counter = 0L;

        public int getCount() {
            return posts.size();
        }

        public Collection<BlogPost> findPosts() {
            return posts.values();
        }

        public Optional<BlogPost> findPostById(long id) {
            return Optional.ofNullable(this.posts.get(id));
        }

        public void addPost(BlogPost post) {
            post.setId(counter++);
            post.setTimestamp(LocalDateTime.now());
            this.posts.put(post.getId(), post);
        }

        public void removePost(long id) {
            this.posts.remove(id);
        }

    }

}
