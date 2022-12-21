package de.schulung.samples.blog.boundary.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class BlogPostDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String title;
    @NotNull
    @Size(min = 10)
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime timestamp;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String author;

}
