package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.domain.BlogPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogPostDtoMapper {

    @Mapping(target = "hashTags", ignore = true)
    BlogPost map(BlogPostDto source);

    BlogPostDto map(BlogPost source);

}
