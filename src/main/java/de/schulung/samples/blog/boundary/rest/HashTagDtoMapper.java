package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.domain.HashTag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashTagDtoMapper {

    HashTag map(HashTagDto source);

    HashTagDto map(HashTag source);

}
