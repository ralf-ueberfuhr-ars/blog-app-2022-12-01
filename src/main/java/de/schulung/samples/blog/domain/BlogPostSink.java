package de.schulung.samples.blog.domain;

import java.util.Collection;
import java.util.Optional;

public interface BlogPostSink {

    int getCount();

    Collection<BlogPost> findPosts();

    Optional<BlogPost> findPostById(long id);

    void addPost(BlogPost post);

    void removePost(long id);

}
