package de.schulung.samples.blog.boundary.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfiguration {

    @Bean
    public SecurityFilterChain disableCsrfChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http.build();
    }

}
