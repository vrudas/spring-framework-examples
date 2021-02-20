package io.sfe.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/error", "/webjars/**").permitAll()
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
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ;
    }
}
