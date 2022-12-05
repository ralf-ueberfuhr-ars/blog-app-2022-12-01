package de.schulung.samples.blog.domain.config;

import de.schulung.samples.blog.domain.BlogPost;

@FunctionalInterface
public interface BlogPostProvider {

    BlogPost createBlogPost();

}
