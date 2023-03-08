package io.sfe.bootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class InMemorySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var user = User.withUsername("user")
            .password("18b146cac8026852b8b9ec406bc84ed2d334a3dc06") //user
            .roles("USER")
            .build();

        var admin = User.withUsername("admin")
            .password("{bcrypt}$2a$10$ku.DZ5JqOy/dgFgAZkwcSuiaMMCmOt8pVmerZDM5lTWO44MHGCMcC") //admin
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * HTTP Basic Example
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and().build();
    }
}
