package de.schulung.samples.blog.boundary.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
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
              .addSecuritySchemes(
                "blogpost_auth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP)
              )
              .addParameters(
                "  blogPostId",
                new Parameter()
                  .in("path")
                  .name("id")
                  .description("The id of the blog post")
                  .schema(new Schema<Integer>().type("integer"))
                  .required(true)
              )
              .addParameters(
                "hashTagName",
                new Parameter()
                  .in("path")
                  .name("name")
                  .description("The name of the hash tag")
                  .schema(new Schema<String>().type("string"))
                  .required(true)
              )
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
