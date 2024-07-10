package com.example.auth_service.controller;

import com.example.auth_service.dto.auth.LoginRequest;
import com.example.auth_service.dto.auth.TokenDto;
import com.example.auth_service.service.UserService;
import com.example.auth_service.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate (@RequestParam String jwt) {
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate (
            @RequestBody @Valid LoginRequest loginRequest) {

        TokenDto authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

}
