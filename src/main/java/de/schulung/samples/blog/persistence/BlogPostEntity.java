package de.schulung.samples.blog.persistence;

import lombok.Data;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class BlogPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    @NotNull
    @Size(min = 10)
    private String content;
    private LocalDateTime timestamp;
    private String author;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> hashTags;

}
