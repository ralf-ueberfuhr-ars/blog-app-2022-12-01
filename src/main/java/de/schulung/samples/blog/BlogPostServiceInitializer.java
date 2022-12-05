package de.schulung.samples.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogPostServiceInitializer {

    private final BlogPostService service;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeSampleData() {
        // TODO we could provide multiple sample data providers here for injection (initial or inclusion)
        if (service.getCount() == 0) {
            log.info("Initializing blog post service with sample data");
            service.addPost(
              BlogPost.builder()
                .title("Mein erster Post")
                .content("Lorem ipsum...")
                .build()
            );
            service.addPost(
              BlogPost.builder()
                .title("Mein zweiter Post")
                .content("Lorem ipsum...")
                .build()
            );
            service.addPost(
              BlogPost.builder()
                .title("Mein dritter Post")
                .content("Lorem ipsum...")
                .build()
            );
        }
    }

}
