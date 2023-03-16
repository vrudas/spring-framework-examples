package io.sfe.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(
        AuthenticationManagerBuilder authenticationManagerBuilder
    ) throws Exception {
        return authenticationManagerBuilder
            .inMemoryAuthentication()
                .withUser("user")
                .password("{bcrypt}$2a$10$GlpFG1Ml3U9AvkOu0D1B9ufnoquX5xqCR/NHaMfBZliYgPa8/e5sK")
                .roles("USER")
            .and()
                .withUser("admin")
                .password("{bcrypt}$2a$10$ku.DZ5JqOy/dgFgAZkwcSuiaMMCmOt8pVmerZDM5lTWO44MHGCMcC")
                .roles("ADMIN")
            .and()
            .getUserDetailsService();
    }
}
