package de.schulung.samples.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostEntity, Long> {
    Collection<BlogPostEntity> findByHashTags(String hashTag);

    // Collection<BlogPostEntity> findByAuthor(String author);

}
