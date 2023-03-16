package io.sfe.jwt.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final String BEARER_TOKEN_NOT_FOUND_MESSAGE =
        "Did not process authentication request since failed to find "
        + BEARER_TOKEN_PREFIX + "token " + HttpHeaders.AUTHORIZATION + " header";

    private static final Runnable EXPIRED_OR_INVALID_JWT_TOKEN_ACTION = () -> {
        throw new BadCredentialsException("Expired or invalid JWT token");
    };

    private final UserDetailsExtractor userDetailsExtractor;

    public JwtAuthorizationFilter(
        AuthenticationManager authenticationManager,
        UserDetailsExtractor userDetailsExtractor
    ) {
        super(authenticationManager);
        this.userDetailsExtractor = userDetailsExtractor;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var token = resolveToken(request);

        if (token == null) {
            logger.warn(BEARER_TOKEN_NOT_FOUND_MESSAGE);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            userDetailsExtractor.extractFromToken(token)
                .map(this::createAuthentication)
                .ifPresentOrElse(this::authenticate, EXPIRED_OR_INVALID_JWT_TOKEN_ACTION);
        } catch (AuthenticationException e) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(BEARER_TOKEN_PREFIX.length());
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );
    }

    private void authenticate(UsernamePasswordAuthenticationToken authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
