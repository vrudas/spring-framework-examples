package io.sfe.jwt.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;

import static io.sfe.jwt.security.jwt.JwtTokenUtil.encodeSecretKey;

@Component
public class JwtTokenGenerator {

    private final String secretKey;
    private final long tokenExpireMilliseconds;
    private final Clock clock;

    public JwtTokenGenerator(
        @Value("${security.jwt.token.secret-key}") String secretKey,
        @Value("${security.jwt.token.expire-milliseconds}") long tokenExpireMilliseconds,
        Clock clock
    ) {
        this.secretKey = secretKey;
        this.tokenExpireMilliseconds = tokenExpireMilliseconds;
        this.clock = clock;
    }

    String generateToken(String username) {
        Date expireDate = new Date(clock.millis() + tokenExpireMilliseconds);

        return Jwts.builder()
            .setSubject(username)
            .setExpiration(expireDate)
            .signWith(Keys.hmacShaKeyFor(encodeSecretKey(secretKey)))
            .compact();
    }

}
