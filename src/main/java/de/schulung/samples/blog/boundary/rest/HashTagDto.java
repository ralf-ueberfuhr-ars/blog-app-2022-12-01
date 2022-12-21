package de.schulung.samples.blog.boundary.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HashTagDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    private String description;

}
