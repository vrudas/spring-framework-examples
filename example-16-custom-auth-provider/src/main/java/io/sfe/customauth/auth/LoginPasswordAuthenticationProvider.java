package io.sfe.customauth.auth;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;

@Primary
@Component
public class LoginPasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public LoginPasswordAuthenticationProvider(
        UserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
    ) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsManager.loadUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, null, emptyList());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
