package de.schulung.samples.blog.domain.config;

import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class InMemoryHashTagSinkConfiguration {

    @Bean
    @ConditionalOnMissingBean(HashTagSink.class)
    HashTagSink inMemoryHashTagSink() {
        log.info("Creating in-memory hash tag sink.");
        return new InMemoryHashTagSink();
    }

    private static class InMemoryHashTagSink implements HashTagSink {

        private final Map<String, HashTag> tags = new HashMap<>();

        @Override
        public HashTag resolveByName(String name) {
            return tags.containsKey(name) ? tags.get(name) : HashTag.builder().name(name).build();
        }

        @Override
        public void save(HashTag hashtag) {
            this.tags.put(hashtag.getName(), hashtag);
        }
    }

}
