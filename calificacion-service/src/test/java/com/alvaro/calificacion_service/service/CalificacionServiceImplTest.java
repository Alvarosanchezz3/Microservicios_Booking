package com.alvaro.calificacion_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.alvaro.calificacion_service.config.EnvLoader;
import com.alvaro.calificacion_service.entity.Calificacion;
import com.alvaro.calificacion_service.repository.CalificacionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalificacionServiceImplTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @InjectMocks
    private CalificacionServiceImpl calificacionService;

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
    void testSaveCalificacion() {
        when(calificacionRepository.save(any(Calificacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Calificacion calificacion = new Calificacion(null, "user1", "hotel1", 5, "Excellent stay");
        Calificacion savedCalificacion = calificacionService.saveCalificacion(calificacion);

        assertThat(savedCalificacion).isNotNull();
        assertThat(savedCalificacion.getId()).isNotNull();
        assertThat(savedCalificacion.getUsuarioId()).isEqualTo("user1");
        assertThat(savedCalificacion.getHotelId()).isEqualTo("hotel1");
        assertThat(savedCalificacion.getCalificacion()).isEqualTo(5);
        assertThat(savedCalificacion.getObservaciones()).isEqualTo("Excellent stay");

        verify(calificacionRepository, times(1)).save(any(Calificacion.class));
    }

    @Test
    void testGetAll() {
        when(calificacionRepository.findAll()).thenReturn(Arrays.asList(calificacion1, calificacion2, calificacion3));

        List<Calificacion> calificaciones = calificacionService.getAll();

        assertThat(calificaciones).hasSize(3);
        assertThat(calificaciones).contains(calificacion1, calificacion2, calificacion3);

        verify(calificacionRepository, times(1)).findAll();
    }

    @Test
    void testGetCalificacionesByUsuarioId() {
        when(calificacionRepository.findByUsuarioId("user1")).thenReturn(Arrays.asList(calificacion1, calificacion2));

        List<Calificacion> calificaciones = calificacionService.getCalificacionesByUsuarioId("user1");

        assertThat(calificaciones).hasSize(2);
        assertThat(calificaciones).contains(calificacion1, calificacion2);

        verify(calificacionRepository, times(1)).findByUsuarioId("user1");
    }

    @Test
    void testGetCalificacionesByHotelId() {
        when(calificacionRepository.findByHotelId("hotel1")).thenReturn(Arrays.asList(calificacion1, calificacion3));

        List<Calificacion> calificaciones = calificacionService.getCalificacionesByHotelId("hotel1");

        assertThat(calificaciones).hasSize(2);
        assertThat(calificaciones).contains(calificacion1, calificacion3);

        verify(calificacionRepository, times(1)).findByHotelId("hotel1");
    }
}
