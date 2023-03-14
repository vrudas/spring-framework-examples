package io.sfe.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/", "/error", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()

            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            .and()

            .oauth2Login()
            .and()

            .logout()
            .logoutSuccessUrl("/").permitAll()
            .and()

            .csrf()
            .ignoringRequestMatchers("/login", "/logout")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ;

        return http.build();
    }
}
