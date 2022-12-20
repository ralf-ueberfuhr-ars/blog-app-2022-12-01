package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaBlogPostSink implements BlogPostSink {

    private final BlogPostRepository repo;
    private final BlogPostEntityMapper mapper;

    @Override
    public int getCount() {
        return (int) repo.count();
    }

    @Override
    public Collection<BlogPost> findPosts() {
        return repo.findAll()
          .stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @Override
    public Collection<BlogPost> findPostsByTag(String name) {
        return repo.findByHashTags(name)
          .stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @Override
    public Optional<BlogPost> findPostById(long id) {
        return repo.findById(id)
          .map(mapper::map);
    }

    @Override
    public void addPost(BlogPost post) {
        BlogPostEntity entity = mapper.map(post);
        BlogPostEntity savedEntity = repo.save(entity); // id generated
        post.setId(savedEntity.getId());
    }

    @Override
    public void removePost(long id) {
        repo.deleteById(id);
    }

    @Override
    public void updatePost(BlogPost post) {
        repo.findById(post.getId())
          .ifPresent(entity -> {
              mapper.copy(post, entity);
              repo.save(entity);
          });
    }
}
