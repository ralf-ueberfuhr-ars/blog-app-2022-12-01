package de.schulung.samples.blog.persistence;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.HashTag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogPostEntityMapper {

    BlogPost map(BlogPostEntity source);

    BlogPostEntity map(BlogPost source);

    void copy(BlogPost source, @MappingTarget BlogPostEntity target);

    default HashTag fromName(String name) {
        return HashTag.builder()
          .name(name)
          .build();
    }

    default String fromHashTag(HashTag hashTag) {
        return hashTag.getName();
    }

}
