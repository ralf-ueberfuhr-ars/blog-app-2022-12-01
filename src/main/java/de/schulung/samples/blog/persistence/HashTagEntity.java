package de.schulung.samples.blog.persistence;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "hashtags")
@Data
public class HashTagEntity {

    @Id
    @NotEmpty
    private String name;
    private String description;

}
