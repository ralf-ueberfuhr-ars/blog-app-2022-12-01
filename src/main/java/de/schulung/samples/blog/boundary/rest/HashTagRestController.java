package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.domain.HashTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Tag(name = OpenApiConstants.TAG_HASHTAG_NAME)
@SecurityRequirement(name = OpenApiConstants.SECURITY_NAME)
public class HashTagRestController {

    private final HashTagService service;
    private final HashTagDtoMapper mapper;

    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping(
      value = "/{name}",
      consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Assign some metadata to a hash tag name")
    @ApiResponse(responseCode = "201", description = "The metadata was assigned for the first time")
    @ApiResponse(responseCode = "204", description = "The metadata already existed and was successfully replaced")
    @ApiResponse(responseCode = "403", description = "The current user does not have AUTHOR role.")
    public ResponseEntity<Void> save(
      @Parameter(ref = OpenApiConstants.HASHTAG_NAME_PARAMETER)
      @PathVariable("name")
      String name,
      @RequestBody
      HashTagDto tag) {
        final boolean existedBefore = service.exists(name);
        tag.setName(name);
        var tagForDomain = mapper.map(tag);
        service.save(tagForDomain);
        return ResponseEntity
          .status(existedBefore ? HttpStatus.NO_CONTENT : HttpStatus.CREATED)
          .build();
    }

}
