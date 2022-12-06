package de.schulung.samples.blog.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class HashTag {

    @NotEmpty
    private String name;
    private String description;

}
