package de.schulung.samples.blog.domain.config;

import de.schulung.samples.blog.domain.BlogPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogPostServiceInitializer {

    private final BlogPostService service;
    private final List<BlogPostProvider> providers;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeSampleData() {
        if (service.getCount() == 0) {
            log.info("Initializing blog post service with sample data");
            providers.stream()
              .map(BlogPostProvider::createBlogPost)
              .filter(Objects::nonNull)
              .forEach(service::addPost);
        }
    }

}
