package com.alvaro.usuario_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.alvaro.usuario_service.FeignService.HotelFeignService;
import com.alvaro.usuario_service.dto.SaveUser;
import com.alvaro.usuario_service.entity.Calificacion;
import com.alvaro.usuario_service.entity.Role;
import com.alvaro.usuario_service.entity.Usuario;
import com.alvaro.usuario_service.repository.RoleRepository;
import com.alvaro.usuario_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HotelFeignService hotelFeignService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private SaveUser saveUser;
    private Usuario usuario;
    private Role role;

    @BeforeEach
    void setUp() {
        saveUser = new SaveUser("John Doe", "johndoe", "password123", "johndoe@example.com");
        usuario = new Usuario(UUID.randomUUID().toString(), "John Doe", "johndoe", "password123", "johndoe@example.com", "Some info", null, null);
        role = new Role(1L, "ROLE_CUSTOMER");
    }

    @Test
    void testSaveUsuario() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_CUSTOMER")).thenReturn(role);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario savedUsuario = usuarioService.saveUsuario(saveUser);

        assertThat(savedUsuario).isNotNull();
        assertThat(savedUsuario.getUsername()).isEqualTo("johndoe");
        verify(kafkaTemplate, times(1)).send(eq("email-topic"), anyString());
    }

    @Test
    void testGetAll() {
        Usuario usuario1 = new Usuario(UUID.randomUUID().toString(), "John Doe", "johndoe", "password123", "johndoe@example.com", "Some info", null, role);
        Usuario usuario2 = new Usuario(UUID.randomUUID().toString(), "Jane Doe", "janedoe", "password456", "janedoe@example.com", "Other info", null, role);

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioService.getAll();

        assertThat(usuarios).isNotEmpty();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @Test
    void testGetUsuarioByUsername() {
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));
        when(restTemplate.getForObject(anyString(), eq(Calificacion[].class))).thenReturn(new Calificacion[0]);

        Usuario foundUsuario = usuarioService.getUsuarioByUsername("johndoe");

        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getUsername()).isEqualTo("johndoe");
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioRepository.findById(anyString())).thenReturn(Optional.of(usuario));
        when(restTemplate.getForObject(anyString(), eq(Calificacion[].class))).thenReturn(new Calificacion[0]);

        Usuario foundUsuario = usuarioService.getUsuarioById(usuario.getId());

        assertThat(foundUsuario).isNotNull();
        assertThat(foundUsuario.getId()).isEqualTo(usuario.getId());
    }
}
