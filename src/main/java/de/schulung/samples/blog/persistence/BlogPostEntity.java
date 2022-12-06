package de.schulung.samples.blog.persistence;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
