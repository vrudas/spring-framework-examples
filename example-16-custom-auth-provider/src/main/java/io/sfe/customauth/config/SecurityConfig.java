package io.sfe.customauth.config;

import io.sfe.customauth.auth.LoginPasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    void configureGlobal(
        AuthenticationManagerBuilder auth,
        LoginPasswordAuthenticationProvider loginPasswordAuthenticationProvider
    ) throws Exception {
        auth.authenticationProvider(loginPasswordAuthenticationProvider)
            .jdbcAuthentication()
            .passwordEncoder(passwordEncoder)
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser("admin")
            .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

}
