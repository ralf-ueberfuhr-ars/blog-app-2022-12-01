package de.schulung.samples.blog.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HashTagMapper {

    void copy(HashTag source, @MappingTarget HashTag target);

}
