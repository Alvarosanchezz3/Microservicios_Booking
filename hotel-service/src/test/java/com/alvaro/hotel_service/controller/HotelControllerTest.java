package com.alvaro.hotel_service.controller;

import com.alvaro.hotel_service.entity.Hotel;
import com.alvaro.hotel_service.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HotelControllerTest {

    // Mock del servicio de Hotel
    @Mock
    private HotelService hotelService;

    // Instancia del controlador a ser probado, con la inyección de los mocks
    @InjectMocks
    private HotelController hotelController;

    // Método de inicialización que se ejecuta antes de cada prueba
    @BeforeEach
    void setUp() {
        // Inicializa los mocks y las anotaciones Mockito
        MockitoAnnotations.openMocks(this);
    }

    // Prueba para verificar el método guardarHotel del controlador
    @Test
    void testGuardarHotel() {
        Hotel hotelRequest = new Hotel("1", "Hotel Test", "Info Test", "Ubicacion Test");
        when(hotelService.saveHotel(any(Hotel.class))).thenReturn(hotelRequest);

        // When (Cuando)
        ResponseEntity<Hotel> responseEntity = hotelController.guardarHotel(new Hotel());

        // Then (Entonces)
        // Verifica que el ResponseEntity tenga el código de estado HTTP 201 (CREATED) y el hotel devuelto sea el esperado
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(hotelRequest);
    }

    // Prueba para verificar el método obtenerHotel del controlador
    @Test
    void testObtenerHotel() {
        String hotelId = "1";
        Hotel hotel = new Hotel(hotelId, "Hotel Test", "Info Test", "Ubicacion Test");
        when(hotelService.getHotel(hotelId)).thenReturn(hotel);

        // When (Cuando)
        ResponseEntity<Hotel> responseEntity = hotelController.obtenerHotel(hotelId);

        // Then (Entonces)
        // Verifica que el ResponseEntity tenga el código de estado HTTP 200 (OK) y el hotel devuelto sea el esperado
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(hotel);
    }

    // Prueba para verificar el método listarHoteles del controlador
    @Test
    void testListarHoteles() {
        List<Hotel> hoteles = Arrays.asList(
                new Hotel("1", "Hotel 1", "Info 1", "Ubicacion 1"),
                new Hotel("2", "Hotel 2", "Info 2", "Ubicacion 2")
        );
        when(hotelService.getAll()).thenReturn(hoteles);

        // When (Cuando)
        ResponseEntity<List<Hotel>> responseEntity = hotelController.listarHoteles();

        // Then (Entonces)
        // Verifica que el ResponseEntity tenga el código de estado HTTP 200 (OK) y la lista de hoteles devuelta sea la esperada
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(hoteles);
    }
}
