package io.sfe.jwt.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements AuthenticationProvider {

    private final UserDetailsManager userDetailsManager;

    public JwtTokenProvider(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        throw new UnsupportedOperationException();
    }
}
