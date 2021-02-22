package io.sfe.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static io.sfe.jwt.security.jwt.JwtTokenUtil.encodeSecretKey;

@Component
public class UserDetailsExtractor {

    private final String secretKey;
    private final UserDetailsService userDetailsService;

    public UserDetailsExtractor(
        @Value("${security.jwt.token.secret-key}") String secretKey,
        UserDetailsService userDetailsService
    ) {
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    Optional<UserDetails> extractFromToken(String token) {
        try {
            var claimsJws = parseToken(token);
            var username = claimsJws.getBody().getSubject();

            return Optional.ofNullable(userDetailsService.loadUserByUsername(username));
        } catch (Exception e) {
            throw new CredentialsExpiredException("Expired or invalid JWT token");
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(encodeSecretKey(secretKey)))
            .build()
            .parseClaimsJws(token);
    }
}
