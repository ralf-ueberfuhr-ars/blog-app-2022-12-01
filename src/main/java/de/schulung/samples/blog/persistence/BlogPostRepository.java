package de.schulung.samples.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostEntity, Long> {

    // Collection<BlogPostEntity> findByAuthor(String author);

}
