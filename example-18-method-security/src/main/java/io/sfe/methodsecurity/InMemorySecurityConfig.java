package io.sfe.methodsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class InMemorySecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var user = User.withUsername("user")
            .password("{bcrypt}$2a$10$GlpFG1Ml3U9AvkOu0D1B9ufnoquX5xqCR/NHaMfBZliYgPa8/e5sK") //user
            .roles("USER")
            .authorities(new SimpleGrantedAuthority("READ_USERS"), new SimpleGrantedAuthority("ROLE_USER"))
            .build();

        var admin = User.withUsername("admin")
            .password("{bcrypt}$2a$10$ku.DZ5JqOy/dgFgAZkwcSuiaMMCmOt8pVmerZDM5lTWO44MHGCMcC") //admin
            .roles("USER", "ADMIN")
            .authorities("DELETE_USERS", "ROLE_ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .formLogin()
        ;

        return http.build();
    }

}
