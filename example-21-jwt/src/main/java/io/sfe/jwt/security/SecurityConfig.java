package io.sfe.jwt.security;

import io.sfe.jwt.security.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
    void configureGlobal(
        AuthenticationManagerBuilder auth,
        JwtTokenProvider jwtTokenProvider
    ) {
        auth.authenticationProvider(jwtTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(this.authenticationManager(), jwtTokenGenerator))
            .addFilter(new JwtAuthorizationFilter(this.authenticationManager(), userDetailsExtractor))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
