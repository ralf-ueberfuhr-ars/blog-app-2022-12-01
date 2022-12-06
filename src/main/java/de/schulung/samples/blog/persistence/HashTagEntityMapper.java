package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.HashTag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashTagEntityMapper {

    HashTag map(HashTagEntity source);

    HashTagEntity map(HashTag source);

}
