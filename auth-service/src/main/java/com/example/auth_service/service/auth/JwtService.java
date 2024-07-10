package com.example.auth_service.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        // Fecha actual
        Date issuedAt = new Date(System.currentTimeMillis());

        // Se pasa el tiempo a milisegundos y se le suma al inicial
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                // Header del JWT
                .header()
                    .type("JWT")
                    .and()

                // Payload del JWT
                .subject(user.getUsername())
                .issuedAt(issuedAt) // Fecha de emisión del token
                .expiration(expiration) // Fecha de expiración del token
                .claims(extraClaims)

                // Firma del JWT y fin del método
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractUsername (String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    public Claims extractAllClaims (String jwt) {
        return Jwts.parser().verifyWith( generateKey() ).build().parseSignedClaims(jwt).getPayload();
    }
}