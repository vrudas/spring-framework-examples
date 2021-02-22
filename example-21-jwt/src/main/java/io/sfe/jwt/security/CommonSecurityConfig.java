package io.sfe.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsManager userDetailsService(
        AuthenticationManagerBuilder authenticationManagerBuilder
    ) throws Exception {
        return authenticationManagerBuilder
            .inMemoryAuthentication()
                .withUser("user")
                .password("$2a$10$xxDN8JuT21uFSH0LeelhKuzv42tXjKKCDgY9gHO4/159cKG1ChICi")
                .roles("USER")
            .and()
                .withUser("admin")
                .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
                .roles("ADMIN")
            .and()
            .getUserDetailsService();
    }
}
