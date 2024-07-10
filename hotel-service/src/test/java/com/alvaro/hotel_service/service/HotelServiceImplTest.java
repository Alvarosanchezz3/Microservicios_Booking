package com.alvaro.hotel_service.service;

import com.alvaro.hotel_service.entity.Hotel;
import com.alvaro.hotel_service.exception.ResourceNotFoundException;
import com.alvaro.hotel_service.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    // Mock del repositorio de Hotel
    @Mock
    private HotelRepository hotelRepository;

    // Instancia del servicio a ser probado, con la inyección de los mocks
    @InjectMocks
    private HotelServiceImpl hotelService;

    // Método de inicialización que se ejecuta antes de cada prueba
    @BeforeEach
    void setUp() {
        // Inicializa los mocks y las anotaciones Mockito
        MockitoAnnotations.openMocks(this);
    }

    // Prueba para verificar el método saveHotel
    @Test
    void testSaveHotel() {
        // Given (Dado)
        Hotel hotelToSave = new Hotel("1", "Hotel Test", "Info Test", "Ubicacion Test");
        // Cuando se llame hotelRepository.save con cualquier instancia de Hotel, retorna hotelToSave
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelToSave);

        // When (Cuando)
        Hotel savedHotel = hotelService.saveHotel(new Hotel());

        // Then (Entonces)
        // Verifica que el hotel guardado no sea nulo y tenga el ID esperado y nombre esperado
        assertThat(savedHotel).isNotNull();
        assertThat(savedHotel.getId()).isEqualTo("1");
        assertThat(savedHotel.getNombre()).isEqualTo("Hotel Test");
    }

    // Prueba para verificar el método getAll
    @Test
    void testGetAll() {
        // Given (Dado)
        List<Hotel> hotels = Arrays.asList(
                new Hotel("1", "Hotel 1", "Info 1", "Ubicacion 1"),
                new Hotel("2", "Hotel 2", "Info 2", "Ubicacion 2")
        );
        // Cuando se llame hotelRepository.findAll, retorna la lista de hoteles definida arriba
        when(hotelRepository.findAll()).thenReturn(hotels);

        // When (Cuando)
        List<Hotel> allHotels = hotelService.getAll();

        // Then (Entonces)
        // Verifica que la lista de hoteles no sea nula y tenga el tamaño y nombres esperados
        assertThat(allHotels).isNotNull();
        assertThat(allHotels.size()).isEqualTo(2);
        assertThat(allHotels.get(0).getNombre()).isEqualTo("Hotel 1");
        assertThat(allHotels.get(1).getNombre()).isEqualTo("Hotel 2");
    }

    // Prueba para verificar el método getHotel
    @Test
    void testGetHotel() {
        // Given (Dado)
        String hotelId = "1";
        Hotel hotel = new Hotel(hotelId, "Hotel Test", "Info Test", "Ubicacion Test");
        // Cuando se llame hotelRepository.findById con hotelId, retorna Optional.of(hotel)
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        // When (Cuando)
        Hotel foundHotel = hotelService.getHotel(hotelId);

        // Then (Entonces)
        // Verifica que el hotel encontrado no sea nulo y tenga el ID y nombre esperados
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isEqualTo(hotelId);
        assertThat(foundHotel.getNombre()).isEqualTo("Hotel Test");
    }

    // Prueba para verificar que se lance ResourceNotFoundException cuando el hotel no se encuentra
    @Test
    void testGetHotelNotFound() {
        // Given (Dado)
        String hotelId = "1";
        // Cuando se llame hotelRepository.findById con hotelId, retorna Optional.empty()
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // When, Then (Cuando, Entonces)
        // Verifica que al llamar hotelService.getHotel con hotelId, se lance ResourceNotFoundException
        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> hotelService.getHotel(hotelId)
        );
        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("Hotel no encontrado con el id: " + hotelId);
    }
}
