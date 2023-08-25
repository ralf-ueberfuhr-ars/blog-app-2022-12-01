package de.schulung.samples.blog.boundary.config;

import de.schulung.samples.blog.shared.config.SecurityRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableMethodSecurity
@Configuration
public class WebDisabledSecurityConfiguration {

    //@Bean
    Authentication authentication() {
return null;
    }



}
