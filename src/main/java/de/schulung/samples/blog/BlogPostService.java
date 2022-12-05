package de.schulung.samples.blog;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogPostService {

    // TODO replace by database
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
