package com.example.auth_service.config.security.filter;


import com.example.auth_service.entity.Usuario;
import com.example.auth_service.service.UserService;
import com.example.auth_service.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtener encabezado llamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        // Si viene vacío o no empieza por "Bearer "
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Que el filterchain siga con los otros filtros
            return;
        }

        // 2. Obtener JWT desde el encabezado -→ Formato: "Bearer jwt"
        String jwt = authorizationHeader.split(" ")[1]; // Divide el encabezado entre el [0] = "Bearer" y [1] = jwt

        // 3. Obtener el username desde el token, esto además valida el token, su formato, la firma y le fecha de expiración
        String username = jwtService.extractUsername(jwt);

        // 4. Settear objeto autenticación dentro de security context holder
        Usuario user = userService.findOneByUsername(username);

        // Obtenemos los authorities de los roles
        List<GrantedAuthority> authorities = userService.getAuthorities(user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
        // Añadimos info del request a la autenticación del context
        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 5. Ejecutar el registro de filtros
        filterChain.doFilter(request, response);
    }
}