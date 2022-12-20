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
import java.util.stream.Collectors;

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

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Collection<BlogPost> findPosts() {
            return posts.values();
        }

        @Override
        public Collection<BlogPost> findPostsByTag(String name) {
            return findPosts().stream()
              .filter(bp -> bp.getHashTags()
                .stream()
                .anyMatch(tag -> tag.getName().equalsIgnoreCase(name))
              )
              .collect(Collectors.toList());
        }

        @Override
        public Optional<BlogPost> findPostById(long id) {
            return Optional.ofNullable(this.posts.get(id));
        }

        @Override
        public void addPost(BlogPost post) {
            post.setId(counter++);
            post.setTimestamp(LocalDateTime.now());
            this.posts.put(post.getId(), post);
        }

        @Override
        public void removePost(long id) {
            this.posts.remove(id);
        }

        @Override
        public void updatePost(BlogPost post) {
            this.posts.put(post.getId(), post);
        }

    }

}
