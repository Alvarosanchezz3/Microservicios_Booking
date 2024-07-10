package com.alvaro.calificacion_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.alvaro.calificacion_service.entity.Calificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CalificacionRepositoryTest {

    @Autowired
    private CalificacionRepository calificacionRepository;

    private Calificacion calificacion1;
    private Calificacion calificacion2;
    private Calificacion calificacion3;

    @BeforeEach
    void setUp() {
        calificacion1 = new Calificacion("1", "user1", "hotel1", 5, "Excellent stay");
        calificacion2 = new Calificacion("2", "user1", "hotel2", 4, "Very good stay");
        calificacion3 = new Calificacion("3", "user2", "hotel1", 3, "Good stay");

        calificacionRepository.save(calificacion1);
        calificacionRepository.save(calificacion2);
        calificacionRepository.save(calificacion3);
    }

    @Test
    void testFindByUsuarioId() {
        List<Calificacion> calificacionesUsuario1 = calificacionRepository.findByUsuarioId("user1");
        List<Calificacion> calificacionesUsuario2 = calificacionRepository.findByUsuarioId("user2");

        assertThat(calificacionesUsuario1).hasSize(2);
        assertThat(calificacionesUsuario1).contains(calificacion1, calificacion2);

        assertThat(calificacionesUsuario2).hasSize(1);
        assertThat(calificacionesUsuario2).contains(calificacion3);
    }

    @Test
    void testFindByHotelId() {
        List<Calificacion> calificacionesHotel1 = calificacionRepository.findByHotelId("hotel1");
        List<Calificacion> calificacionesHotel2 = calificacionRepository.findByHotelId("hotel2");

        assertThat(calificacionesHotel1).hasSize(2);
        assertThat(calificacionesHotel1).contains(calificacion1, calificacion3);

        assertThat(calificacionesHotel2).hasSize(1);
        assertThat(calificacionesHotel2).contains(calificacion2);
    }
}
