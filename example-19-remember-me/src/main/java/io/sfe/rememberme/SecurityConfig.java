package io.sfe.rememberme;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user")
                .password("$2a$10$j5p/7VDz5g2PEXHPBw30VugVGLWK9zUA9WMPD0IkUpGBZPwUKHEaG")
                .roles("USER")
            .and()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password("$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/anonymous*").anonymous()
            .antMatchers("/login*").permitAll()
            .anyRequest().authenticated()
            .and()

            .formLogin()
            .and()

            .logout().deleteCookies("JSESSIONID")
            .and()

            .rememberMe()
                .key("uniqueAndSecret")
                .rememberMeCookieName("remember-me")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(15)
//                .useSecureCookie(true) // RememberMe only on HTTPS secured
//                .tokenRepository(jdbcTokenRepository())
        ;
    }

}
