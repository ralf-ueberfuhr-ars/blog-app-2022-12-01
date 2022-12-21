package de.schulung.samples.blog.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain disableCsrfForRestApiChain(HttpSecurity http) throws Exception {
        http.formLogin();
        http.httpBasic();
        http.authorizeHttpRequests().anyRequest().permitAll();
        http
          .csrf()
          .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/**"))
          .disable();
        return http.build();
    }

}