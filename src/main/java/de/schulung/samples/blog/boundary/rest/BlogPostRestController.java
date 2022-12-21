package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.boundary.NotFoundException;
import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import de.schulung.samples.blog.domain.HashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class BlogPostRestController {

    private final BlogPostService service;
    private final BlogPostDtoMapper mapper;

    @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('READER')")
    public Collection<BlogPostDto> findAll() {
        return service.findPosts()
          .stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @GetMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('READER')")
    public BlogPostDto findById(@PathVariable("id") long id) {
        return service.findPostById(id)
          .map(mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<BlogPostDto> create(
      @Valid @RequestBody BlogPostDto post,
      Authentication authentication
    ) {
        post.setAuthor(authentication.getName());
        var postForDomain = mapper.map(post);
        service.addPost(postForDomain);
        var postForJson = mapper.map(postForDomain);
        URI location = linkTo(methodOn(BlogPostRestController.class).findById(postForJson.getId())).toUri();
        return ResponseEntity.created(location).body(post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id, Authentication authentication) {
        BlogPost blogPost = service.findPostById(id).orElseThrow(NotFoundException::new);
        String currentUser = authentication.getName();
        if (blogPost.getAuthor() == null || blogPost.getAuthor().equalsIgnoreCase(currentUser)) {
            service.removePost(id);
        } else {
            throw new AccessDeniedException("blog post is managed by another author");
        }

    }

    @GetMapping(
      value = "/{id}/tags",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('READER')")
    public Collection<HashTag> findTagsForPost(@PathVariable("id") long id) {
        return service.findPostById(id)
          .orElseThrow(NotFoundException::new)
          .getHashTags();
    }

    @PutMapping(
      value = "/{id}/tags",
      consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('AUTHOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTagsForPost(
      @PathVariable("id") long id,
      @RequestBody String[] tagNames
    ) {
        BlogPost blogPost = service.findPostById(id).orElseThrow(NotFoundException::new);
        blogPost.setHashTags(
          Stream.of(tagNames)
            .filter(Predicate.not(String::isBlank))
            .map(name -> HashTag.builder().name(name).build())
            .collect(Collectors.toList())
        );
        service.update(blogPost, false);
    }

}
