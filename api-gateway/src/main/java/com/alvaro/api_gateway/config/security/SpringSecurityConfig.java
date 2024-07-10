package com.alvaro.api_gateway.config.security;

import com.alvaro.api_gateway.config.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter JwtAuthenticationFilter;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
            return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(JwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(this::buildPathMatchers)
                .build();
    }

    private void buildPathMatchers(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec) {
        authorizeExchangeSpec
                .pathMatchers(HttpMethod.POST, "/auth/authenticate").permitAll()
                .pathMatchers(HttpMethod.GET, "/auth/validate-token").permitAll()
                .pathMatchers(HttpMethod.POST, "/usuarios").permitAll()

                .pathMatchers(HttpMethod.GET, "/usuarios").hasAnyAuthority("ADMIN_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/usuarios/username/{username}").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/usuarios/id/{id}").hasAnyAuthority("ADMIN_AUTHORITIES")

                .pathMatchers(HttpMethod.POST, "/hoteles").hasAnyAuthority("ADMIN_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/hoteles").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/hoteles/{id}").hasAnyAuthority("ADMIN_AUTHORITIES")

                .pathMatchers(HttpMethod.POST, "/calificaciones").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/calificaciones").hasAnyAuthority("ADMIN_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/calificaciones/usuarios/{usuarioId}").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")
                .pathMatchers(HttpMethod.GET, "/calificaciones/hoteles/{hotelId}").hasAnyAuthority("ADMIN_AUTHORITIES", "CUSTOMER_AUTHORITIES")

                .anyExchange().authenticated();
    }
}