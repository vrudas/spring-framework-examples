package io.sfe.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(new Pbkdf2PasswordEncoder())
            .withUser(
                User.builder()
                    .username("user")
                    .password("bacecc4236b50d27c7e580d6f7ec844251cdbec028b545154b3734fa63075b82017fb649be13fd17")
                    .roles("USER")
                    .authorities(new SimpleGrantedAuthority("READ_USERS"))
                    .build()
            );

        auth.inMemoryAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .withUser("admin")
            .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .authorities("DELETE_USERS")
            .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/login").permitAll()
            .mvcMatchers("/admin/**").hasRole("ADMIN")
            .mvcMatchers("/read-user").hasAnyAuthority("READ_USERS")
            .mvcMatchers("/delete-user").access("hasRole('ADMIN') and hasAuthority('DELETE_USERS')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
        ;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return roleHierarchy;
    }
}
