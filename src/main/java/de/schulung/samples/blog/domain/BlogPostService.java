package de.schulung.samples.blog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostSink sink;
    private final HashTagResolver hashTagResolver;

    public int getCount() {
        return sink.getCount();
    }

    public Collection<BlogPost> findPosts() {
        final var result = sink.findPosts();
        result.stream()
          .map(BlogPost::getHashTags)
          .flatMap(Collection::stream)
          .forEach(hashTagResolver::resolve);
        return result;
    }

    public Collection<BlogPost> findPostsByTag(String name) {
        final var result = sink.findPostsByTag(name);
        result.stream()
          .map(BlogPost::getHashTags)
          .flatMap(Collection::stream)
          .forEach(hashTagResolver::resolve);
        return result;
    }

    public Optional<BlogPost> findPostById(long id) {
        final var result = sink.findPostById(id);
        result
          .stream()
          .map(BlogPost::getHashTags)
          .flatMap(Collection::stream)
          .forEach(hashTagResolver::resolve);
        return result;
    }

    public void addPost(@Valid BlogPost post) {
        post.setTimestamp(LocalDateTime.now());
        this.sink.addPost(post);
    }

    public void removePost(long id) {
        this.sink.removePost(id);
    }

    public void update(@Valid BlogPost post, boolean updateTimeStamp) {
        if (updateTimeStamp) {
            post.setTimestamp(LocalDateTime.now());
        }
        this.sink.updatePost(post);
    }

}
