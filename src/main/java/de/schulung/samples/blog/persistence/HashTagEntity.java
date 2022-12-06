package de.schulung.samples.blog.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "hashtags")
@Data
public class HashTagEntity {

    @Id
    @NotEmpty
    private String name;
    private String description;

}
