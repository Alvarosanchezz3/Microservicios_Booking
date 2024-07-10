package com.alvaro.usuario_service.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    private Usuario usuario; // Instancia de Usuario a ser probada en los tests

    @BeforeEach
    public void setUp() {
        usuario = new Usuario(); // Crea una nueva instancia de Usuario antes de cada test
    }

    @Test
    public void testNoArgsConstructor() {
        Usuario emptyUsuario = new Usuario(); // Crea un usuario vacío utilizando el constructor sin argumentos
        assertThat(emptyUsuario).isNotNull(); // Verifica que el usuario vacío no sea nulo
    }

    @Test
    public void testAllArgsConstructor() {
        // Crea un usuario completo utilizando el constructor con todos los argumentos
        Usuario fullUsuario = new Usuario("1", "John Doe", "johndoe", "password123", "johndoe@example.com", "Some info", null, null);

        // Verifica que el usuario creado no sea nulo y que los atributos sean los esperados
        assertThat(fullUsuario).isNotNull();
        assertThat(fullUsuario.getId()).isEqualTo("1");
        assertThat(fullUsuario.getName()).isEqualTo("John Doe");
        assertThat(fullUsuario.getUsername()).isEqualTo("johndoe");
        assertThat(fullUsuario.getPassword()).isEqualTo("password123");
        assertThat(fullUsuario.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(fullUsuario.getInfo()).isEqualTo("Some info");
        assertThat(fullUsuario.getCalificaciones()).isNull();
        assertThat(fullUsuario.getRole()).isNull();
    }

    @Test
    public void testSettersAndGetters() {
        // Configura los valores de los atributos del usuario utilizando setters
        usuario.setId("1");
        usuario.setName("John Doe");
        usuario.setUsername("johndoe");
        usuario.setPassword("password123");
        usuario.setEmail("johndoe@example.com");
        usuario.setInfo("Some info");
        Calificacion[] calificaciones = new Calificacion[]{};
        usuario.setCalificaciones(calificaciones);
        Role role = new Role();
        usuario.setRole(role);

        // Verifica que los valores obtenidos a través de getters sean los mismos que se configuraron
        assertThat(usuario.getId()).isEqualTo("1");
        assertThat(usuario.getName()).isEqualTo("John Doe");
        assertThat(usuario.getUsername()).isEqualTo("johndoe");
        assertThat(usuario.getPassword()).isEqualTo("password123");
        assertThat(usuario.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(usuario.getInfo()).isEqualTo("Some info");
        assertThat(usuario.getCalificaciones()).isEqualTo(calificaciones);
        assertThat(usuario.getRole()).isEqualTo(role);
    }
}
