package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaHashTagSink implements HashTagSink {

    private final HashTagRepository repo;
    private final HashTagEntityMapper mapper;

    @Override
    public HashTag resolveByName(String name) {
        return repo.findById(name)
          .map(mapper::map)
          .orElse(HashTag.builder().name(name).build());
    }

    @Override
    public void save(HashTag hashtag) {
        repo.save(mapper.map(hashtag));
    }
}
