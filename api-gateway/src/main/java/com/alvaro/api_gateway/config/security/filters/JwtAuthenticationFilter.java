package com.alvaro.api_gateway.config.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private ReactiveAuthenticationManager reactAuthManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .map(token -> token.replace("Bearer ", ""))
                .flatMap(token -> reactAuthManager.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
                .flatMap(authentication -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }
}
