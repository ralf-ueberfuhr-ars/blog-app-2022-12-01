package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.boundary.NotFoundException;
import de.schulung.samples.blog.boundary.rest.ApiSecurity.OnlyApiAuthors;
import de.schulung.samples.blog.boundary.rest.ApiSecurity.OnlyApiReaders;
import de.schulung.samples.blog.domain.BlogPost;
import de.schulung.samples.blog.domain.BlogPostService;
import de.schulung.samples.blog.domain.HashTag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
@Tag(name = OpenApiConstants.TAG_BLOGPOST_NAME)
public class BlogPostRestController {

    private final BlogPostService service;
    private final BlogPostDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyApiReaders
    @Operation(summary = "Read all blog posts")
    @ApiResponse(responseCode = "200", description = "The blog posts were found and returned.")
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
    @OnlyApiReaders
    @Operation(summary = "Read a single blog post")
    @ApiResponse(responseCode = "200", description = "Post was found")
    @ApiResponse(responseCode = "404", description = "Blog post could not be found")
    public BlogPostDto findById(
      @Parameter(ref = OpenApiConstants.BLOGPOST_ID_PARAMETER)
      @PathVariable("id")
      long id
    ) {
        return service.findPostById(id)
          .map(mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    @OnlyApiAuthors
    @Operation(summary = "Create a blog post")
    @ApiResponse(responseCode = "201", description = "Post was created successfully",
      headers = @Header(name = "Location", description = "URL to the newly created blog post")
    )
    @ApiResponse(responseCode = "422", description = "Blog post is invalid")
    public ResponseEntity<BlogPostDto> create(
      @Valid
      @RequestBody
      BlogPostDto post,
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
    @OnlyApiAuthors
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a single blog post")
    @ApiResponse(responseCode = "204", description = "Post was deleted successfully")
    @ApiResponse(responseCode = "403",
      description = "The current user does not have AUTHOR role or is not the author of the blog post. "
        + "Anonymous blog posts can be deleted by every author."
    )
    @ApiResponse(responseCode = "404", description = "Post could not be found")
    public void delete(
      @Parameter(ref = OpenApiConstants.BLOGPOST_ID_PARAMETER)
      @PathVariable("id")
      long id,
      Authentication authentication
    ) {
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
    @OnlyApiReaders
    @Tag(name = OpenApiConstants.TAG_HASHTAG_NAME)
    @Operation(summary = "Read the hash tags of a single blog post")
    @ApiResponse(responseCode = "200", description = "Post was found")
    @ApiResponse(responseCode = "404", description = "Post could not be found")
    public Collection<HashTag> findTagsForPost(
      @Parameter(ref = OpenApiConstants.BLOGPOST_ID_PARAMETER)
      @PathVariable("id")
      long id
    ) {
        return service.findPostById(id)
          .orElseThrow(NotFoundException::new)
          .getHashTags();
    }

    @PutMapping(
      value = "/{id}/tags",
      consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @OnlyApiAuthors
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = OpenApiConstants.TAG_HASHTAG_NAME)
    @Operation(summary = "Assign hash tags to a blog post")
    @ApiResponse(responseCode = "204", description = "Tags were successfully assigned")
    @ApiResponse(responseCode = "404", description = "Post could not be found")
    public void updateTagsForPost(
      @Parameter(ref = OpenApiConstants.BLOGPOST_ID_PARAMETER)
      @PathVariable("id")
      long id,
      @RequestBody
      String[] tagNames
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
