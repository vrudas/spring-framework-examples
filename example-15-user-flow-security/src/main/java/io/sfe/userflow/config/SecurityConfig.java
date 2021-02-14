package io.sfe.userflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(
        AuthenticationManagerBuilder auth,
        DataSource dataSource,
        PasswordEncoder passwordEncoder
    ) throws Exception {
        return auth.jdbcAuthentication()
            .passwordEncoder(passwordEncoder)
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser("admin")
            .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .roles("ADMIN")
            .and()
            .getUserDetailsService();
    }

}
