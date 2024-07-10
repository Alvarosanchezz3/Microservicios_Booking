package com.alvaro.usuario_service.controller;

import com.alvaro.usuario_service.dto.SaveUser;
import com.alvaro.usuario_service.entity.Usuario;
import com.alvaro.usuario_service.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario (@RequestBody @Valid SaveUser usuarioRequest) {
        Usuario usuario = usuarioService.saveUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios () {
        List<Usuario> usuarioList = usuarioService.getAll();
        return ResponseEntity.ok(usuarioList);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioPorUsername (@PathVariable String username) {
        Usuario usuario = usuarioService.getUsuarioByUsername(username);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/id/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Usuario> obtenerUsuarioPorId (@PathVariable String id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<Usuario> ratingHotelFallback (String usuarioId, Exception e) {
        log.info("Método fallback activado por el fallo de un servicio: ", e.getMessage());
        Usuario usuario = Usuario.builder()
                .email("usuario-predeterminado@gmail.com")
                .name("usuario-predeterminado")
                .info("Usuario creado por defecto cuando se cae un servicio")
                .id("1234")
                .build();
        return ResponseEntity.ok(usuario);
    }
}
