package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.BlogPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogPostEntityMapper {

    BlogPost map(BlogPostEntity source);

    BlogPostEntity map(BlogPost source);

}
