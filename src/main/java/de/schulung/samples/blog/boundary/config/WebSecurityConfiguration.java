package de.schulung.samples.blog.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    @Profile("!unsecure")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
         * TODO REST APIs should not use basic authentication, because it relies on cookies (stateful)
         *  - use JWT, OAuth or any other token based authentication
         *  - Swagger UI uses Authentication header automatically, but browser does not
         */
        return http
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                // disable sessions -> good for REST API, bad for classic web (stores auth)
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher("/api/**")))
                .build();
    }

    @Bean
    @Profile("unsecure")
    public SecurityFilterChain filterChainUnsecure(HttpSecurity http) throws Exception {
        /*
         * TODO REST APIs should not use basic authentication, because it relies on cookies (stateful)
         *  - use JWT, OAuth or any other token based authentication
         *  - Swagger UI uses Authentication header automatically, but browser does not
         */
        return http
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // disable sessions -> good for REST API, bad for classic web (stores auth)
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher("/api/**")))
                .build();
    }

}
