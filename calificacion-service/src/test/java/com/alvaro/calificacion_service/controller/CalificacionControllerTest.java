package com.alvaro.calificacion_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import com.alvaro.calificacion_service.config.EnvLoader;
import com.alvaro.calificacion_service.entity.Calificacion;
import com.alvaro.calificacion_service.service.CalificacionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.ArgumentMatchers;

@WebMvcTest(CalificacionController.class)
public class CalificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalificacionService calificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Calificacion calificacion1;
    private Calificacion calificacion2;
    private Calificacion calificacion3;

    @BeforeAll
    public static void setUpEnv() {
        EnvLoader.loadEnvVariables("../.env"); // Carga de variables de entorno desde un archivo .env
    }

    @BeforeEach
    void setUp() {
        calificacion1 = new Calificacion("1", "user1", "hotel1", 5, "Excellent stay");
        calificacion2 = new Calificacion("2", "user1", "hotel2", 4, "Very good stay");
        calificacion3 = new Calificacion("3", "user2", "hotel1", 3, "Good stay");
    }

    @Test
    void testGuardarCalificacion() throws Exception {
        when(calificacionService.saveCalificacion(ArgumentMatchers.any(Calificacion.class))).thenReturn(calificacion1);

        mockMvc.perform(post("/calificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(calificacion1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(calificacion1.getId())))
                .andExpect(jsonPath("$.usuarioId", is(calificacion1.getUsuarioId())))
                .andExpect(jsonPath("$.hotelId", is(calificacion1.getHotelId())))
                .andExpect(jsonPath("$.calificacion", is(calificacion1.getCalificacion())))
                .andExpect(jsonPath("$.observaciones", is(calificacion1.getObservaciones())));

        verify(calificacionService, times(1)).saveCalificacion(ArgumentMatchers.any(Calificacion.class));
    }

    @Test
    void testListarCalificaciones() throws Exception {
        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2, calificacion3);
        when(calificacionService.getAll()).thenReturn(calificaciones);

        mockMvc.perform(get("/calificaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(calificacion1.getId())))
                .andExpect(jsonPath("$[1].id", is(calificacion2.getId())))
                .andExpect(jsonPath("$[2].id", is(calificacion3.getId())));

        verify(calificacionService, times(1)).getAll();
    }

    @Test
    void testListarCalificacionesPorUsuarioId() throws Exception {
        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);
        when(calificacionService.getCalificacionesByUsuarioId("user1")).thenReturn(calificaciones);

        mockMvc.perform(get("/calificaciones/usuarios/user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(calificacion1.getId())))
                .andExpect(jsonPath("$[1].id", is(calificacion2.getId())));

        verify(calificacionService, times(1)).getCalificacionesByUsuarioId("user1");
    }

    @Test
    void testListarCalificacionesPorHotelId() throws Exception {
        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion3);
        when(calificacionService.getCalificacionesByHotelId("hotel1")).thenReturn(calificaciones);

        mockMvc.perform(get("/calificaciones/hoteles/hotel1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(calificacion1.getId())))
                .andExpect(jsonPath("$[1].id", is(calificacion3.getId())));

        verify(calificacionService, times(1)).getCalificacionesByHotelId("hotel1");
    }
}
