package io.sfe.rememberme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var user = User.withUsername("user")
            .password("{bcrypt}$2a$10$j5p/7VDz5g2PEXHPBw30VugVGLWK9zUA9WMPD0IkUpGBZPwUKHEaG")
            .roles("USER")
            .build();

        var admin = User.withUsername("admin")
            .password("{bcrypt}$2a$10$kF9qWGfBqKqqO9PuG/XLZuuPq601zbtV3F4v8.mYVX0ilBsvbjjpW")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/anonymous*").anonymous()
            .requestMatchers("/login*").permitAll()
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

        return http.build();
    }

}
