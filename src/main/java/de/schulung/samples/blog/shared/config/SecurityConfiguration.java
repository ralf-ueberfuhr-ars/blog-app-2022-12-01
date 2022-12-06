package de.schulung.samples.blog.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
          User.builder()
            .username("user1")
            .password(encoder.encode("user1"))
            .roles(SecurityRoles.READER.getName())
            .build(),
          User.builder()
            .username("user2")
            .password(encoder.encode("user2"))
            .roles(SecurityRoles.READER.getName(), SecurityRoles.AUTHOR.getName())
            .build()
        );
    }

}
