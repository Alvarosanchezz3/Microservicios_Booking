package com.alvaro.hotel_service.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HotelTest {

    private Hotel hotel; // Instancia de Hotel a ser probada en los tests

    @BeforeEach
    public void setUp() {
        hotel = new Hotel(); // Crea una nueva instancia de Hotel antes de cada test
    }

    @Test
    public void testNoArgsConstructor() {
        Hotel emptyHotel = new Hotel(); // Crea un hotel vacío utilizando el constructor sin argumentos
        assertThat(emptyHotel).isNotNull(); // Verifica que el hotel vacío no sea nulo
    }

    @Test
    public void testAllArgsConstructor() {
        // Crea un hotel completo utilizando el constructor con todos los argumentos
        Hotel fullHotel = new Hotel("1", "Hotel Test", "Info Test", "Ubicacion Test");

        // Verifica que el hotel creado no sea nulo y que los atributos sean los esperados
        assertThat(fullHotel).isNotNull();
        assertThat(fullHotel.getId()).isEqualTo("1");
        assertThat(fullHotel.getNombre()).isEqualTo("Hotel Test");
        assertThat(fullHotel.getInfo()).isEqualTo("Info Test");
        assertThat(fullHotel.getUbicacion()).isEqualTo("Ubicacion Test");
    }

    @Test
    public void testSettersAndGetters() {
        // Configura los valores de los atributos del hotel utilizando setters
        hotel.setId("1");
        hotel.setNombre("Hotel Test");
        hotel.setInfo("Info Test");
        hotel.setUbicacion("Ubicacion Test");

        // Verifica que los valores obtenidos a través de getters sean los mismos que se configuraron
        assertThat(hotel.getId()).isEqualTo("1");
        assertThat(hotel.getNombre()).isEqualTo("Hotel Test");
        assertThat(hotel.getInfo()).isEqualTo("Info Test");
        assertThat(hotel.getUbicacion()).isEqualTo("Ubicacion Test");
    }
}