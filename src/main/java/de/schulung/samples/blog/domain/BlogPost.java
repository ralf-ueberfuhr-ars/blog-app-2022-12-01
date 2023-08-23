package de.schulung.samples.blog.domain;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BlogPost {

    private Long id;
    @NotNull
    @Size(min=3)
    private String title;
    @NotNull
    @Size(min=10)
    private String content;
    private LocalDateTime timestamp;
    private String author;
    private List<HashTag> hashTags;

}
