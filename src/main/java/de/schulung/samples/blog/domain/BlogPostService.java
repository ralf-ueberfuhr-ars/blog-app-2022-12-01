package de.schulung.samples.blog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class BlogPostService {

    // TODO use Lombok's @Delegate?
    private final BlogPostSink sink;

    public int getCount() {
        return sink.getCount();
    }

    public Collection<BlogPost> findPosts() {
        return sink.findPosts();
    }

    public Optional<BlogPost> findPostById(long id) {
        return sink.findPostById(id);
    }

    public void addPost(@Valid BlogPost post) {
        post.setTimestamp(LocalDateTime.now());
        this.sink.addPost(post);
    }

    public void removePost(long id) {
        this.sink.removePost(id);
    }

}
