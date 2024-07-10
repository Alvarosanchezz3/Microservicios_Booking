package com.alvaro.calificacion_service.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalificacionTest {

    private Calificacion calificacion;

    @BeforeEach
    void setUp() {
        calificacion = new Calificacion();
    }

    @Test
    void testNoArgsConstructor() {
        Calificacion emptyCalificacion = new Calificacion();
        assertThat(emptyCalificacion).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        Calificacion fullCalificacion = new Calificacion("1", "user1", "hotel1", 5, "Excellent stay");

        assertThat(fullCalificacion).isNotNull();
        assertThat(fullCalificacion.getId()).isEqualTo("1");
        assertThat(fullCalificacion.getUsuarioId()).isEqualTo("user1");
        assertThat(fullCalificacion.getHotelId()).isEqualTo("hotel1");
        assertThat(fullCalificacion.getCalificacion()).isEqualTo(5);
        assertThat(fullCalificacion.getObservaciones()).isEqualTo("Excellent stay");
    }

    @Test
    void testSettersAndGetters() {
        calificacion.setId("1");
        calificacion.setUsuarioId("user1");
        calificacion.setHotelId("hotel1");
        calificacion.setCalificacion(5);
        calificacion.setObservaciones("Excellent stay");

        assertThat(calificacion.getId()).isEqualTo("1");
        assertThat(calificacion.getUsuarioId()).isEqualTo("user1");
        assertThat(calificacion.getHotelId()).isEqualTo("hotel1");
        assertThat(calificacion.getCalificacion()).isEqualTo(5);
        assertThat(calificacion.getObservaciones()).isEqualTo("Excellent stay");
    }
}
