package com.alvaro.hotel_service.controller;

import com.alvaro.hotel_service.entity.Hotel;
import com.alvaro.hotel_service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelRepository;
    
    @PostMapping
    public ResponseEntity<Hotel> guardarHotel (@RequestBody Hotel HotelRequest) {
        Hotel Hotel = hotelRepository.saveHotel(HotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Hotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> obtenerHotel (@PathVariable String id) {
        Hotel Hotel = hotelRepository.getHotel(id);
        return ResponseEntity.ok(Hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> listarHoteles () {
        List<Hotel> HotelList = hotelRepository.getAll();
        return ResponseEntity.ok(HotelList);
    }
}
