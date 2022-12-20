package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaHashTagSink implements HashTagSink {

    private final HashTagRepository repo;
    private final HashTagEntityMapper mapper;

    @Override
    public boolean exists(String name) {
        return repo.existsById(name);
    }

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

    @Override
    public Collection<HashTag> findAll() {
        return repo.findAll().stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }
}
