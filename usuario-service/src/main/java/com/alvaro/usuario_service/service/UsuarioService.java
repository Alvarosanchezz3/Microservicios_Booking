package com.alvaro.usuario_service.service;

import com.alvaro.usuario_service.dto.SaveUser;
import com.alvaro.usuario_service.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario saveUsuario(SaveUser saveUser);

    List<Usuario> getAll();

    Usuario getUsuarioById(String usuarioid);

    Usuario getUsuarioByUsername(String usuarioid);
}
