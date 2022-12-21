package de.schulung.samples.blog.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain disableCsrfForRestApiChain(HttpSecurity http) throws Exception {
        /*
         * TODO REST APIs should not use basic authentication, because it relies on cookies (stateful)
         *  - use JWT, OAuth or any other token based authentication
         *  - Swagger UI uses Authentication header automatically, but browser does not
         */
        return http
          .formLogin()
          .and()
          .httpBasic()
          // disable sessions -> good for REST API, bad for classic web (stores auth)
          //.and()
          //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authorizeHttpRequests()
          .anyRequest().authenticated()
          .and()
          .csrf().ignoringAntMatchers("/api/**").and().build();
    }

}
