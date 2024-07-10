package com.alvaro.hotel_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.alvaro.hotel_service.config.EnvLoader;
import com.alvaro.hotel_service.entity.Hotel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest // Anotación que configura las pruebas para utilizar una base de datos en memoria
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository; // Repositorio de hoteles a ser probado

    private Hotel hotel; // Instancia de Hotel para utilizar en las pruebas

    // Método que se ejecuta una sola vez antes de todos los métodos de prueba
    @BeforeAll
    public static void setUpEnv() {
        EnvLoader.loadEnvVariables("../.env"); // Carga de variables de entorno desde un archivo .env
    }

    // Método que se ejecuta antes de cada método de prueba
    @BeforeEach
    public void setUp() {
        // Crea una instancia de Hotel para usar en las pruebas
        hotel = new Hotel("1", "Hotel Test", "Info Test", "Ubicacion Test");
    }

    // Prueba para verificar el método findById del repositorio
    @Test
    public void testFindById() {
        // Guarda el hotel en la base de datos
        hotelRepository.save(hotel);
        // Busca el hotel por su ID
        Optional<Hotel> foundHotel = hotelRepository.findById("1");
        // Verifica que el hotel encontrado no sea nulo
        assertThat(foundHotel).isPresent();
        // Verifica que el nombre del hotel encontrado sea el esperado
        assertThat(foundHotel.get().getNombre()).isEqualTo("Hotel Test");
    }

    // Prueba para verificar el método deleteById del repositorio
    @Test
    public void testDeleteHotel() {
        // Guarda el hotel en la base de datos
        hotelRepository.save(hotel);
        // Elimina el hotel por su ID
        hotelRepository.deleteById("1");
        // Intenta buscar el hotel eliminado por su ID y verifica que no esté presente
        Optional<Hotel> deletedHotel = hotelRepository.findById("1");
        assertThat(deletedHotel).isNotPresent();
    }

    // Prueba para verificar el método save (que actualiza) del repositorio
    @Test
    public void testUpdateHotel() {
        // Guarda el hotel en la base de datos
        hotelRepository.save(hotel);
        // Busca el hotel por su ID y lanza una excepción si no se encuentra
        Hotel updatedHotel = hotelRepository.findById("1").orElseThrow();
        // Actualiza el nombre del hotel
        updatedHotel.setNombre("Hotel Updated");
        // Guarda el hotel actualizado en la base de datos
        hotelRepository.save(updatedHotel);

        // Busca nuevamente el hotel por su ID y verifica que esté presente
        Optional<Hotel> foundHotel = hotelRepository.findById("1");
        assertThat(foundHotel).isPresent();
        // Verifica que el nombre del hotel encontrado sea el nombre actualizado
        assertThat(foundHotel.get().getNombre()).isEqualTo("Hotel Updated");
    }
}