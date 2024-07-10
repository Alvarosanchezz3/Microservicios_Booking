package com.alvaro.usuario_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.alvaro.usuario_service.config.EnvLoader;
import com.alvaro.usuario_service.entity.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    public static void setUpEnv() {
        EnvLoader.loadEnvVariables("../.env"); // Carga de variables de entorno desde un archivo .env
    }

    @Test
    public void testFindByUsername() {
        // Crea una nueva instancia de Usuario y guárdala en la base de datos
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setName("John Doe");
        usuario.setUsername("johndoe");
        usuario.setPassword("password123");
        usuario.setEmail("johndoe@example.com");
        usuario.setInfo("Some info");

        usuarioRepository.save(usuario);

        // Verifica que el método findByUsername devuelve el usuario correcto
        Optional<Usuario> foundUsuario = usuarioRepository.findByUsername("johndoe");
        assertThat(foundUsuario).isPresent();
        assertThat(foundUsuario.get().getUsername()).isEqualTo("johndoe");
        assertThat(foundUsuario.get().getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    public void testSaveAndFindById() {
        // Crea una nueva instancia de Usuario y guárdala en la base de datos
        Usuario usuario = new Usuario();
        usuario.setId("2");
        usuario.setName("Jane Doe");
        usuario.setUsername("janedoe");
        usuario.setPassword("password456");
        usuario.setEmail("janedoe@example.com");
        usuario.setInfo("Some other info");

        usuarioRepository.save(usuario);

        // Verifica que el método findById devuelve el usuario correcto
        Optional<Usuario> foundUsuario = usuarioRepository.findById("2");
        assertThat(foundUsuario).isPresent();
        assertThat(foundUsuario.get().getId()).isEqualTo("2");
        assertThat(foundUsuario.get().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testDeleteById() {
        // Crea una nueva instancia de Usuario y guárdala en la base de datos
        Usuario usuario = new Usuario();
        usuario.setId("3");
        usuario.setName("Jim Doe");
        usuario.setUsername("jimdoe");
        usuario.setPassword("password789");
        usuario.setEmail("jimdoe@example.com");
        usuario.setInfo("Yet another info");

        usuarioRepository.save(usuario);

        // Elimina el usuario
        usuarioRepository.deleteById("3");

        // Verifica que el usuario ya no está presente en la base de datos
        Optional<Usuario> foundUsuario = usuarioRepository.findById("3");
        assertThat(foundUsuario).isNotPresent();
    }
}