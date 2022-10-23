package com.group.inventory.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private final String SECRET_KEY =
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret" +
            "whatisasecret";

    private final String PREFIX = "Bearer ";

    private final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateJwtToken(String username) {

        Date currentDate = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + 86400000))
                .signWith(KEY ,SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwt(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e1) {
            log.error("JWT token is not supported: {}", e1);
        } catch (MalformedJwtException e2) {
            log.error("Invalid Token: {}", e2);
        } catch (SignatureException e3) {
            log.error("Invalid signature: {}", e3);
        } catch (ExpiredJwtException e4) {
            log.error("JWT is expired: {}", e4);
        } catch (IllegalArgumentException e5) {
            log.error("JWT Claims is empty: {}", e5);
        }

        return false;
    }

    public String getToken(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");

        if (jwt == null)
            return null;

        return jwt.substring(PREFIX.length(), jwt.length());
    }

    public String getEmail(String token) {
        if (!validateJwt(token))
            return null;

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
