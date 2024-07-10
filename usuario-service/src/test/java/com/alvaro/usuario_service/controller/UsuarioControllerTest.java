package com.alvaro.usuario_service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.alvaro.usuario_service.config.EnvLoader;
import com.alvaro.usuario_service.dto.SaveUser;
import com.alvaro.usuario_service.entity.Usuario;
import com.alvaro.usuario_service.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private SaveUser saveUser;
    private Usuario usuario;

    @BeforeAll
    public static void setUpEnv() {
        EnvLoader.loadEnvVariables("../.env"); // Carga de variables de entorno desde un archivo .env
    }

    @BeforeEach
    void setUp() {
        saveUser = new SaveUser("John Doe", "johndoe", "password123", "johndoe@example.com");
        usuario = new Usuario(UUID.randomUUID().toString(), "John Doe", "johndoe", "password123", "johndoe@example.com", "Some info", null, null);
    }

    @Test
    void testGuardarUsuario() throws Exception {
        when(usuarioService.saveUsuario(any(SaveUser.class))).thenReturn(usuario);

        MvcResult result = mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveUser)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Usuario returnedUsuario = objectMapper.readValue(responseBody, Usuario.class);

        assertThat(returnedUsuario).isNotNull();
        assertThat(returnedUsuario.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void testListarUsuarios() throws Exception {
        Usuario usuario1 = new Usuario(UUID.randomUUID().toString(), "John Doe", "johndoe", "password123", "johndoe@example.com", "Some info", null, null);
        Usuario usuario2 = new Usuario(UUID.randomUUID().toString(), "Jane Doe", "janedoe", "password456", "janedoe@example.com", "Other info", null, null);

        List<Usuario> usuarioList = Arrays.asList(usuario1, usuario2);

        when(usuarioService.getAll()).thenReturn(usuarioList);

        MvcResult result = mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<Usuario> returnedUsuarios = Arrays.asList(objectMapper.readValue(responseBody, Usuario[].class));

        assertThat(returnedUsuarios).isNotEmpty();
        assertThat(returnedUsuarios.size()).isEqualTo(2);
    }

    @Test
    void testObtenerUsuarioPorUsername() throws Exception {
        when(usuarioService.getUsuarioByUsername(any(String.class))).thenReturn(usuario);

        MvcResult result = mockMvc.perform(get("/usuarios/username/johndoe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Usuario returnedUsuario = objectMapper.readValue(responseBody, Usuario.class);

        assertThat(returnedUsuario).isNotNull();
        assertThat(returnedUsuario.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void testObtenerUsuarioPorId() throws Exception {
        when(usuarioService.getUsuarioById(any(String.class))).thenReturn(usuario);

        MvcResult result = mockMvc.perform(get("/usuarios/id/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Usuario returnedUsuario = objectMapper.readValue(responseBody, Usuario.class);

        assertThat(returnedUsuario).isNotNull();
        assertThat(returnedUsuario.getId()).isEqualTo(usuario.getId());
    }
}
