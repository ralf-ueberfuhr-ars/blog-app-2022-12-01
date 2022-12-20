package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagService;
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
public class HashTagRestController {

    private final HashTagService service;

    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping(
      value = "/{name}",
      consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> save(
      @PathVariable("name") String name,
      @RequestBody HashTag tag) {
        final boolean existedBefore = service.exists(name);
        tag.setName(name);
        service.save(tag);
        return ResponseEntity
          .status(existedBefore ? HttpStatus.NO_CONTENT : HttpStatus.CREATED)
          .build();
    }

}
