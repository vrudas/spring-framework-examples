package io.sfe.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class InMemorySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    void configureAuthenticationManager(
        AuthenticationManagerBuilder auth
    ) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(
                User.builder()
                    .username("user")
                    .password("{bcrypt}$2a$10$GlpFG1Ml3U9AvkOu0D1B9ufnoquX5xqCR/NHaMfBZliYgPa8/e5sK")
                    .roles("USER")
                    .authorities(new SimpleGrantedAuthority("READ_USERS"))
                    .build()
            );

        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{bcrypt}$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .authorities("DELETE_USERS")
            .roles("USER", "ADMIN");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .authorizeHttpRequests(authRegistry -> authRegistry
                .requestMatchers("/login").permitAll()
                .requestMatchers("/read-user/**").hasAnyAuthority("READ_USERS")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/delete-user/**").access(
                    new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasAuthority('DELETE_USERS')")
                )
                .anyRequest().authenticated()
            )
            .formLogin();

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_GUEST");

        return roleHierarchy;
    }
}
