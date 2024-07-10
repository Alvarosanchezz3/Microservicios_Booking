package com.alvaro.usuario_service.service;

import com.alvaro.usuario_service.FeignService.HotelFeignService;
import com.alvaro.usuario_service.dto.SaveUser;
import com.alvaro.usuario_service.entity.Calificacion;
import com.alvaro.usuario_service.entity.Hotel;
import com.alvaro.usuario_service.entity.Role;
import com.alvaro.usuario_service.entity.Usuario;
import com.alvaro.usuario_service.event.CreateUserEvent;
import com.alvaro.usuario_service.exception.ResourceNotFoundException;
import com.alvaro.usuario_service.repository.RoleRepository;
import com.alvaro.usuario_service.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelFeignService hotelFeignService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Usuario saveUsuario(SaveUser saveUser) {
        // Crear un nuevo usuario
        Usuario user = new Usuario();

        // Generar un ID aleatorio usando UUID
        String randomId = UUID.randomUUID().toString();
        user.setId(randomId);

        // Asignar los datos del usuario desde SaveUser
        user.setName(saveUser.getName());
        user.setUsername(saveUser.getUsername());
        user.setPassword(passwordEncoder.encode(saveUser.getPassword()));
        user.setEmail(saveUser.getEmail());

        // Obtener el rol "Customer" desde el repositorio de roles
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");

        // Asignar el rol al usuario
        user.setRole(customerRole);

        // Guardar el usuario en la base de datos
        Usuario savedUser = usuarioRepository.save(user);

        // Si el usuario se guarda correctamente, realiza esto:
        if (savedUser != null) {
            CreateUserEvent userEvent = new CreateUserEvent();
            userEvent.setName(savedUser.getName());
            userEvent.setUsername(savedUser.getUsername());
            userEvent.setEmail(savedUser.getEmail());

            kafkaTemplate.send("email-topic", userEvent.toString());
            logger.info("Nuevo usuario registrado y enviado a Email-service con Kafka: {}", userEvent);
        }

        return savedUser;
    }

    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                // Obtengo las calificaciones usando RestTemplate
                Calificacion[] calificacionesUsuario = restTemplate.getForObject(
                        "http://calificacion-service/calificaciones/usuarios/" + usuario.getId(), Calificacion[].class);

                if (calificacionesUsuario != null && calificacionesUsuario.length > 0) {
                    logger.info("El usuario {} tiene las siguientes calificaciones:", usuario.getName());

                    // Recorro el arreglo de calificaciones y obtengo el hotel correspondiente
                    for (Calificacion calificacion : calificacionesUsuario) {
                        String hotelId = calificacion.getHotelId();

                        // Obtengo el hotel usando Feign Client
                        Hotel hotel = hotelFeignService.obtenerHotel(hotelId);
                        calificacion.setHotel(hotel);
                        logger.info("Calificación ID: {}, Calificación: {}, Hotel: {}", calificacion.getId(), calificacion.getCalificacion(), hotel.toString());
                    }
                } else {
                    logger.info("El usuario {} no tiene calificaciones.", usuario.getName());
                }

                // Asigno las calificaciones al usuario
                usuario.setCalificaciones(calificacionesUsuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario getUsuarioByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Usuario no encontrado con el id: " + username));

        // Obtengo las calificaciones usando RestTemplate
        Calificacion[] calificacionesUsuario = restTemplate.getForObject(
                "http://calificacion-service/calificaciones/usuarios/" + usuario.getId(), Calificacion[].class);

        if (calificacionesUsuario != null && calificacionesUsuario.length > 0) {
            logger.info("El usuario {} tiene las siguientes calificaciones:", usuario.getName());

            // Con un for each recorro el arreglo de calificaciones y cojo el id del hotel de cada una y obtengo su hotel con ello
            for (Calificacion calificacion : calificacionesUsuario) {
                String hotelId = calificacion.getHotelId();

                // Obtengo el hotel usando Feign Client
                Hotel hotel = hotelFeignService.obtenerHotel(hotelId);
                calificacion.setHotel(hotel);
                logger.info("Calificación ID: {}, Calificación: {}, Hotel: {}", calificacion.getId(), calificacion.getCalificacion(), hotel.toString());
            }
        } else {
            logger.info("El usuario {} no tiene calificaciones.", usuario.getName());
        }

        usuario.setCalificaciones(calificacionesUsuario);
        return usuario;
    }

    @Override
    public Usuario getUsuarioById(String id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario no encontrado con el id: " + id));

        // Obtengo las calificaciones usando RestTemplate
        Calificacion[] calificacionesUsuario = restTemplate.getForObject(
                "http://calificacion-service/calificaciones/usuarios/" + usuario.getId(), Calificacion[].class);

        if (calificacionesUsuario != null && calificacionesUsuario.length > 0) {
            logger.info("El usuario {} tiene las siguientes calificaciones:", usuario.getName());

            // Con un for each recorro el arreglo de calificaciones y cojo el id del hotel de cada una y obtengo su hotel con ello
            for (Calificacion calificacion : calificacionesUsuario) {
                String hotelId = calificacion.getHotelId();
                
                // Obtengo el hotel usando Feign Client
                Hotel hotel = hotelFeignService.obtenerHotel(hotelId);
                calificacion.setHotel(hotel);
                logger.info("Calificación ID: {}, Calificación: {}, Hotel: {}", calificacion.getId(), calificacion.getCalificacion(), hotel.toString());
            }
        } else {
            logger.info("El usuario {} no tiene calificaciones.", usuario.getName());
        }

        usuario.setCalificaciones(calificacionesUsuario);
        return usuario;
    }
}