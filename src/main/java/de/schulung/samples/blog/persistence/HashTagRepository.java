package de.schulung.samples.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTagEntity, String> {
}
