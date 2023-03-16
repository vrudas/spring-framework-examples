package io.sfe.jwt.security;

import io.sfe.jwt.security.jwt.JwtAuthenticationFilter;
import io.sfe.jwt.security.jwt.JwtAuthorizationFilter;
import io.sfe.jwt.security.jwt.JwtTokenGenerator;
import io.sfe.jwt.security.jwt.JwtTokenProvider;
import io.sfe.jwt.security.jwt.UserDetailsExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final UserDetailsExtractor userDetailsExtractor;

    public SecurityConfig(
        JwtTokenGenerator jwtTokenGenerator,
        UserDetailsExtractor userDetailsExtractor
    ) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userDetailsExtractor = userDetailsExtractor;
    }

    @Autowired
    void configureAuthenticationManager(
        AuthenticationManagerBuilder auth,
        JwtTokenProvider jwtTokenProvider
    ) {
        auth.authenticationProvider(jwtTokenProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        http
            .cors()
            .and()
            .csrf().disable()

            .authorizeHttpRequests()
            .requestMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtTokenGenerator))
            .addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsExtractor))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
