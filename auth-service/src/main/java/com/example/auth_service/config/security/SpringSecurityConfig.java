package com.example.auth_service.config.security;

import brave.Tracer;
import com.example.auth_service.client.UserFeignClient;
import com.example.auth_service.entity.Usuario;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringSecurityConfig {

    private final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private UserFeignClient feignClient;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {

        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder( passwordEncoder() );
        authenticationStrategy.setUserDetailsService( userDetailsService() );

        return authenticationStrategy;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return (username) -> {
            try {
                Usuario usuario = feignClient.obtenerUsuarioPorUsername(username).getBody();

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(usuario.getRole().getName()));

                return new User(usuario.getUsername(), usuario.getPassword(), true,
                        true, true, true, authorities);
                
            } catch (FeignException e) {
                log.error("No existe el usuario " + username);
                tracer.currentSpan().tag("error.message", "No existe el usuario " + username);
                throw new UsernameNotFoundException("No existe el usuario " + username);
            }
        };
    }
}