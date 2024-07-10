package com.alvaro.calificacion_service.controller;

import com.alvaro.calificacion_service.entity.Calificacion;
import com.alvaro.calificacion_service.service.CalificacionService;
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
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PostMapping
    public ResponseEntity<Calificacion> guardarCalificacion (@RequestBody Calificacion calificacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionService.saveCalificacion(calificacion));
    }

    @GetMapping
    public ResponseEntity<List<Calificacion>> listarCalificaciones () {
        return ResponseEntity.ok(calificacionService.getAll());
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Calificacion>> listarCalificacionesPorUsuarioId (@PathVariable String usuarioId) {
        return ResponseEntity.ok(calificacionService.getCalificacionesByUsuarioId(usuarioId));
    }

    @GetMapping("/hoteles/{hotelId}")
    public ResponseEntity<List<Calificacion>> listarCalificacionesPorHotelId (@PathVariable String hotelId) {
        return ResponseEntity.ok(calificacionService.getCalificacionesByHotelId(hotelId));
    }
}
