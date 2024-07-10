package com.alvaro.usuario_service.repository;


import com.alvaro.usuario_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    public Optional<Usuario> findByUsername (String username);
}
