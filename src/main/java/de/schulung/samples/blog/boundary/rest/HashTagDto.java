package de.schulung.samples.blog.boundary.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
@Schema(name = "HashTag")
public class HashTagDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    @NotEmpty
    private String description;

}
