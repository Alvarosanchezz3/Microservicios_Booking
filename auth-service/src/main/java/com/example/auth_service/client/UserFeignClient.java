package com.example.auth_service.client;

import com.example.auth_service.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UserFeignClient {

    @GetMapping("/usuarios/username/{username}")
    ResponseEntity<Usuario> obtenerUsuarioPorUsername (@PathVariable String username);
}
