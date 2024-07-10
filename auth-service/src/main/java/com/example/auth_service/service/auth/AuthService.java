package com.example.auth_service.service.auth;

import com.example.auth_service.dto.auth.LoginRequest;
import com.example.auth_service.dto.SaveUser;
import com.example.auth_service.dto.auth.TokenDto;
import com.example.auth_service.entity.Usuario;
import com.example.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("username", usuario.getUsername());
            extraClaims.put("role", usuario.getRole().getName());

        List<GrantedAuthority> authorities = userService.getAuthorities(usuario);

        extraClaims.put("authorities", authorities);

        return extraClaims;
    }

    public TokenDto login(LoginRequest authRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        Usuario user = userService.findOneByUsername(authRequest.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        String jwt = jwtService.generateToken(userDetails, generateExtraClaims(user));

        TokenDto authResponse = new TokenDto();
        authResponse.setJwt(jwt);

        return authResponse;
    }

    public boolean validateToken(String jwt) {

        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Usuario findLoggedInUser() {
        Authentication auth = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getPrincipal().toString();

        return userService.findOneByUsername(username);
    }
}