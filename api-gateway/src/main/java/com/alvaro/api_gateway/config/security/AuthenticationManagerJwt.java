package com.alvaro.api_gateway.config.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

    @Value("${security.jwt.secret-key}")
    private String secret_key;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        return Mono.just(authentication.getCredentials().toString())
                .map(token -> {
                    // Asegúrate de usar la misma clave y firma utilizadas en el auth Service
                    SecretKey key = generateKey();
                    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
                })
                .map(claims -> {
                    String username = claims.getSubject();
                    String role = claims.get("role", String.class);
                    List<GrantedAuthority> authorities = new ArrayList<>();

                    if (role.equals("ROLE_ADMIN")) {
                        authorities.add(new SimpleGrantedAuthority("ADMIN_AUTHORITIES"));
                    } else {
                        authorities.add(new SimpleGrantedAuthority("CUSTOMER_AUTHORITIES"));
                    }

                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                });
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
}