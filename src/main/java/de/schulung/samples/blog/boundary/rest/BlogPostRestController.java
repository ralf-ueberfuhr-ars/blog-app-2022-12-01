package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class BlogPostRestController {

    private final BlogPostService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('READER')")
    public Collection<BlogPost> findAll() {
        return service.findPosts();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('READER')")
    public BlogPost findById(@PathVariable("id") long id) {
        return null;
    }

    @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<BlogPost> create(@RequestBody BlogPost post, Authentication authentication) {
        post.setAuthor(authentication.getName());
        service.addPost(post);
        URI location = linkTo(methodOn(BlogPostRestController.class).findById(post.getId())).toUri();
        return ResponseEntity.created(location).body(post);
    }

}
