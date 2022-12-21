package de.schulung.samples.blog.boundary.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
          .components(
            new Components()
              .addSecuritySchemes("  blogpost_auth", new SecurityScheme().type(SecurityScheme.Type.HTTP))
          )
          .info(
            new Info()
              .title("Blog Post API")
              .description("Manage blog posts and assign hash tags")
              .version("1.0.0")
          )
          .addTagsItem(
            new Tag()
              .name("blogpost")
              .description("Everything about your blog posts")
          )
          .addTagsItem(
            new Tag()
              .name("hashtag")
              .description("Everything about the hash tags")
          );
    }

}
