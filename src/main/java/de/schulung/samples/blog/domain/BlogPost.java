package de.schulung.samples.blog.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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


}
