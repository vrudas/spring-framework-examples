package io.sfe.customauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class CommonSecurityConfig {

    /**
     * This bean can be replaced by a custom implementation (often case)
     */
    @Bean
    public UserDetailsManager userDetailsService(DataSource dataSource) {
        var admin = User.withUsername("admin")
            .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .roles("ADMIN")
            .build();

        var users = new JdbcUserDetailsManager(dataSource);
        users.createUser(admin);

        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
