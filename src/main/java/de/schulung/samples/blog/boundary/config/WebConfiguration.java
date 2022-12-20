package de.schulung.samples.blog.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.IF_MATCH;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;
import static org.springframework.http.HttpHeaders.LINK;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpHeaders.ORIGIN;

@Configuration
public class WebConfiguration {

    @Bean
    WebMvcConfigurer configureWeb(CorsConfigurationProperties allowed) {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/", "/index.html");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                  .exposedHeaders(LOCATION, LINK)
                  .allowedHeaders(ORIGIN, CONTENT_TYPE, ACCEPT, ACCEPT_LANGUAGE, IF_MATCH, IF_NONE_MATCH, AUTHORIZATION)
                  .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                  .allowedOrigins(allowed.getOrigins())
                  .allowCredentials(allowed.isCredentials());
            }
        };
    }

}
