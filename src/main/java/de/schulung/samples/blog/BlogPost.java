package de.schulung.samples.blog;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogPost {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime timestamp;
    private String author;


}
